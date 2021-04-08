## 004-bean-utl

Bean属性拷贝，主要针对几个常用的拷贝框架进行性能对比，以及功能扩展支持

选用的框架

- cglib (直接使用Spring封装的BeanCopier)
- apache
- MapStruct
- Spring
- HuTool

## I.背景

当业务量不大时，不管选择哪个框架都没什么问题，只要功能支持就ok了；但是当数据量大的时候，可能就需要考虑性能问题了；再实际的项目中，正好遇到了这个问题，不仅慢，还发现会有锁竞争，这特么就尼普了

项目中使用的是Spring的 BeanUtils， 版本 `3.2.4.RELEASE`， 版本相对较老，主要问题在于`org.springframework.beans.CachedIntrospectionResults.forClass`

```java
/**
 * Create CachedIntrospectionResults for the given bean class.
 * <P>We don't want to use synchronization here. Object references are atomic,
 * so we can live with doing the occasional unnecessary lookup at startup only.
 * @param beanClass the bean class to analyze
 * @return the corresponding CachedIntrospectionResults
 * @throws BeansException in case of introspection failure
 */
static CachedIntrospectionResults forClass(Class beanClass) throws BeansException {
    CachedIntrospectionResults results;
    Object value;
    synchronized (classCache) {
        value = classCache.get(beanClass);
    }
    if (value instanceof Reference) {
        Reference ref = (Reference) value;
        results = (CachedIntrospectionResults) ref.get();
    }
    else {
        results = (CachedIntrospectionResults) value;
    }
    if (results == null) {
        if (ClassUtils.isCacheSafe(beanClass, CachedIntrospectionResults.class.getClassLoader()) ||
                isClassLoaderAccepted(beanClass.getClassLoader())) {
            results = new CachedIntrospectionResults(beanClass);
            synchronized (classCache) {
                classCache.put(beanClass, results);
            }
        }
        else {
            if (logger.isDebugEnabled()) {
                logger.debug("Not strongly caching class [" + beanClass.getName() + "] because it is not cache-safe");
            }
            results = new CachedIntrospectionResults(beanClass);
            synchronized (classCache) {
                classCache.put(beanClass, new WeakReference<CachedIntrospectionResults>(results));
            }
        }
    }
    return results;
}
```

看上面的实现，每次获取value都加了一个同步锁，而且还是锁的全局的`classCache`，这就有些过分了啊，微妙的是这段代码注释，谷歌翻译之后为

> 我们不想在这里使用同步。 对象引用是原子的，因此我们可以只在启动时进行偶尔的不必要查找。

这意思大概是说我就在启动的时候用一下，并不会频繁的使用，所以使用了同步代码块也问题不大...

但是在`BeanUtils#copyProperties`中就蛋疼了，每次都会执行这个方法，扎心了

---

当然我们现在一般用的Spring5+了，这段代码也早就做了改造了，新版的如下，不再存在上面的这个并发问题了

```java
/**
 * Create CachedIntrospectionResults for the given bean class.
 * @param beanClass the bean class to analyze
 * @return the corresponding CachedIntrospectionResults
 * @throws BeansException in case of introspection failure
 */
@SuppressWarnings("unchecked")
static CachedIntrospectionResults forClass(Class<?> beanClass) throws BeansException {
    CachedIntrospectionResults results = strongClassCache.get(beanClass);
    if (results != null) {
        return results;
    }
    results = softClassCache.get(beanClass);
    if (results != null) {
        return results;
    }

    results = new CachedIntrospectionResults(beanClass);
    ConcurrentMap<Class<?>, CachedIntrospectionResults> classCacheToUse;

    if (ClassUtils.isCacheSafe(beanClass, CachedIntrospectionResults.class.getClassLoader()) ||
            isClassLoaderAccepted(beanClass.getClassLoader())) {
        classCacheToUse = strongClassCache;
    }
    else {
        if (logger.isDebugEnabled()) {
            logger.debug("Not strongly caching class [" + beanClass.getName() + "] because it is not cache-safe");
        }
        classCacheToUse = softClassCache;
    }

    CachedIntrospectionResults existing = classCacheToUse.putIfAbsent(beanClass, results);
    return (existing != null ? existing : results);
}
```

## II. 不同框架使用姿势

接下来我们看一下几种常见的bean拷贝框架的使用姿势，以及对比测试

### 1. apache BeanUtils

阿里规范中，明确说明了，不要使用它，idea安装阿里的代码规范插件之后，会有提示

使用姿势比较简单，引入依赖

```xml
<!-- https://mvnrepository.com/artifact/commons-beanutils/commons-beanutils -->
<dependency>
    <groupId>commons-beanutils</groupId>
    <artifactId>commons-beanutils</artifactId>
    <version>1.9.4</version>
</dependency>
```

属性拷贝

```java
@Component
public class ApacheCopier {
    public <K, T> T copy(K source, Class<T> target) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        T res = target.newInstance();
        // 注意，第一个参数为target，第二个参数为source
        // 与其他的正好相反 
        BeanUtils.copyProperties(res, source);
        return res;
    }
}
```

### 2. cglib BeanCopier

cglib是通过动态代理的方式来实现属性拷贝的，与上面基于反射实现方式存在本质上的区别，这也是它性能更优秀的主因

在Spring环境下，一般不需要额外的引入依赖；或者直接引入`spring-core`

```xml
<!--      cglib  -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-core</artifactId>
    <version>5.2.8.RELEASE</version>
    <scope>compile</scope>
</dependency>
```

属性拷贝

```java
@Component
public class SpringCglibCopier {
    /**
     * cglib 对象转换
     *
     * @param source
     * @param target
     * @param <K>
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public <K, T> T copy(K source, Class<T> target) throws IllegalAccessException, InstantiationException {
        BeanCopier copier = BeanCopier.create(source.getClass(), target, false);
        T res = target.newInstance();
        copier.copy(source, res, null);
        return res;
    }
}
```

当然也可以直接使用纯净版的cglib，引入依赖

```xml
<dependency>
    <groupId>cglib</groupId>
    <artifactId>cglib</artifactId>
    <version>3.3.0</version>
</dependency>
```

使用姿势和上面一模一样

```java
@Component
public class PureCglibCopier {
    /**
     * cglib 对象转换
     *
     * @param source
     * @param target
     * @param <K>
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public <K, T> T copy(K source, Class<T> target) throws IllegalAccessException, InstantiationException {
        BeanCopier copier = BeanCopier.create(source.getClass(), target, false);
        T res = target.newInstance();
        copier.copy(source, res, null);
        return res;
    }
}
```

### 3. spring BeanUtils

这里使用的是spring `5.2.1.RELEASE`， 就不要拿3.2来使用了，不然并发下的性能实在是感人

> 基于内省+反射，借助getter/setter方法实现属性拷贝，性能比apache高

核心依赖

```java
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-beans</artifactId>
    <version>5.2.1.RELEASE</version>
    <scope>compile</scope>
</dependency>
```

属性拷贝

```java
@Component
public class SpringBeanCopier {

    /**
     * 对象转换
     *
     * @param source
     * @param target
     * @param <K>
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public <K, T> T copy(K source, Class<T> target) throws IllegalAccessException, InstantiationException {
        T res = target.newInstance();
        BeanUtils.copyProperties(source, res);
        return res;
    }
}
```

### 4. hutool BeanUtil

hutool 提供了很多的java工具类，从测试效果来看它的性能比apache会高一点，当低于spring

引入依赖

```xml
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-core</artifactId>
    <version>5.6.0</version>
</dependency>
```

使用姿势

```java
@Component
public class HutoolCopier {

    /**
     * bean 对象转换
     *
     * @param source
     * @param target
     * @param <K>
     * @param <T>
     * @return
     */
    public <K, T> T copy(K source, Class<T> target) throws Exception {
        return BeanUtil.toBean(source, target);
    }
}
```

### 5. MapStruct 

MapStruct 性能更强悍了，缺点也比较明显，需要声明bean的转换接口，自动代码生成的方式来实现拷贝，性能媲美直接的get/set

引入依赖

```xml
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.4.2.Final</version>
</dependency>
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>1.4.2.Final</version>
</dependency>
```

使用姿势

```java
@Mapper
public interface MapStructCopier {
    Target copy(Source source);
}

@Component
public class MapsCopier {
    private MapStructCopier mapStructCopier = Mappers.getMapper(MapStructCopier.class);

    public Target copy(Source source, Class<Target> target) {
        return mapStructCopier.copy(source);
    }
}
```

缺点也比较明显，需要显示的接口转换声明

### 6. 测试

定义两个Bean，用于转换测试，两个bean的成员属性名，类型完全一致

```java
@Data
public class Source {
    private Integer id;
    private String user_name;
    private Double price;
    private List<Long> ids;
    private BigDecimal marketPrice;
}

@Data
public class Target {
    private Integer id;
    private String user_name;
    private Double price;
    private List<Long> ids;
    private BigDecimal marketPrice;
}
```

#### 6.1 功能测试

```java
private Random random = new Random();

public Source genSource() {
    Source source = new Source();
    source.setId(random.nextInt());
    source.setIds(Arrays.asList(random.nextLong(), random.nextLong(), random.nextLong()));
    source.setMarketPrice(new BigDecimal(random.nextFloat()));
    source.setPrice(random.nextInt(120) / 10.0d);
    source.setUser_name("一灰灰Blog");
    return source;
}


 private void copyTest() throws Exception {
        Source s = genSource();
        Target ta = apacheCopier.copy(s, Target.class);
        Target ts = springBeanCopier.copy(s, Target.class);
        Target tc = springCglibCopier.copy(s, Target.class);
        Target tp = pureCglibCopier.copy(s, Target.class);
        Target th = hutoolCopier.copy(s, Target.class);
        Target tm = mapsCopier.copy(s, Target.class);
        System.out.println("source:\t" + s + "\napache:\t" + ta + "\nspring:\t" + ts
                + "\nsCglib:\t" + tc + "\npCglib:\t" + tp + "\nhuTool:\t" + th + "\nmapStruct:\t" + tm);
}
```

输出结果如下，满足预期

```bash
source:	Source(id=1337715455, user_name=一灰灰Blog, price=7.1, ids=[7283949433132389385, 3441022909341384204, 8273318310870260875], marketPrice=0.04279220104217529296875)
apache:	Target(id=1337715455, user_name=一灰灰Blog, price=7.1, ids=[7283949433132389385, 3441022909341384204, 8273318310870260875], marketPrice=0.04279220104217529296875)
spring:	Target(id=1337715455, user_name=一灰灰Blog, price=7.1, ids=[7283949433132389385, 3441022909341384204, 8273318310870260875], marketPrice=0.04279220104217529296875)
sCglib:	Target(id=1337715455, user_name=一灰灰Blog, price=7.1, ids=[7283949433132389385, 3441022909341384204, 8273318310870260875], marketPrice=0.04279220104217529296875)
pCglib:	Target(id=1337715455, user_name=一灰灰Blog, price=7.1, ids=[7283949433132389385, 3441022909341384204, 8273318310870260875], marketPrice=0.04279220104217529296875)
huTool:	Target(id=1337715455, user_name=一灰灰Blog, price=7.1, ids=[7283949433132389385, 3441022909341384204, 8273318310870260875], marketPrice=0.04279220104217529296875)
mapStruct:	Target(id=1337715455, user_name=一灰灰Blog, price=7.1, ids=[7283949433132389385, 3441022909341384204, 8273318310870260875], marketPrice=0.04279220104217529296875)
```

#### 6.2 性能测试

接下来我们关注一下不同的工具包，实现属性拷贝的性能对比情况如何

```java
public void test() throws Exception {
    // 第一次用于预热
    autoCheck(Target2.class, 10000);
    autoCheck(Target2.class, 10000);
    autoCheck(Target2.class, 10000_0);
    autoCheck(Target2.class, 50000_0);
    autoCheck(Target2.class, 10000_00);
}

private <T> void autoCheck(Class<T> target, int size) throws Exception {
    StopWatch stopWatch = new StopWatch();
    runCopier(stopWatch, "apacheCopier", size, (s) -> apacheCopier.copy(s, target));
    runCopier(stopWatch, "springCglibCopier", size, (s) -> springCglibCopier.copy(s, target));
    runCopier(stopWatch, "pureCglibCopier", size, (s) -> pureCglibCopier.copy(s, target));
    runCopier(stopWatch, "hutoolCopier", size, (s) -> hutoolCopier.copy(s, target));
    runCopier(stopWatch, "springBeanCopier", size, (s) -> springBeanCopier.copy(s, target));
    runCopier(stopWatch, "mapStruct", size, (s) -> mapsCopier.copy(s, target));
    System.out.println((size / 10000) + "w -------- cost: " + stopWatch.prettyPrint());
}

private <T> void runCopier(StopWatch stopWatch, String key, int size, CopierFunc func) throws Exception {
    stopWatch.start(key);
    for (int i = 0; i < size; i++) {
        Source s = genSource();
        func.apply(s);
    }
    stopWatch.stop();
}

@FunctionalInterface
public interface CopierFunc<T> {
    T apply(Source s) throws Exception;
}
```

输出结果如下

```bash
1w -------- cost: StopWatch '': running time = 583135900 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
488136600  084%  apacheCopier
009363500  002%  springCglibCopier
009385500  002%  pureCglibCopier
053982900  009%  hutoolCopier
016976500  003%  springBeanCopier
005290900  001%  mapStruct

10w -------- cost: StopWatch '': running time = 5607831900 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
4646282100  083%  apacheCopier
096047200  002%  springCglibCopier
093815600  002%  pureCglibCopier
548897800  010%  hutoolCopier
169937400  003%  springBeanCopier
052851800  001%  mapStruct

50w -------- cost: StopWatch '': running time = 27946743000 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
23115325200  083%  apacheCopier
481878600  002%  springCglibCopier
475181600  002%  pureCglibCopier
2750257900  010%  hutoolCopier
855448400  003%  springBeanCopier
268651300  001%  mapStruct

100w -------- cost: StopWatch '': running time = 57141483600 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
46865332600  082%  apacheCopier
1019163600  002%  springCglibCopier
1033701100  002%  pureCglibCopier
5897726100  010%  hutoolCopier
1706155900  003%  springBeanCopier
619404300  001%  mapStruct
```

| -            | 1w                  | 10w                 | 50w                  | 100w                 |
| ------------ | ------------------- | ------------------- | -------------------- | -------------------- |
| apache       | 0.488136600s / 084% | 4.646282100s / 083% | 23.115325200s / 083% | 46.865332600s / 083% |
| spring cglib | 0.009363500s / 002% | 0.096047200s / 002% | 0.481878600s / 002%  | 1.019163600s / 002%  |
| pure cglibg  | 0.009385500s / 002% | 0.093815600s / 002% | 0.475181600s / 002%  | 1.033701100s / 002%  |
| hutool       | 0.053982900s / 009% | 0.548897800s / 010% | 2.750257900s / 010%  | 5.897726100s / 010%  |
| spring       | 0.016976500s / 003% | 0.169937400s / 003% | 0.855448400s / 003%  | 1.706155900s / 003%  |
| mapstruct    | 0.005290900s / 001% | 0.052851800s / 001% | 0.268651300s / 001%  | 0.619404300s / 001%  |
| total        | 0.583135900s        | 5.607831900s        | 27.946743000s        | 57.141483600s        |

上面的测试中，存在一个不同的变量，即不是用相同的source对象来测试不同的工具转换情况，但是这个不同并不会太影响不同框架的性能对比，基本上从上面的运行结果来看

- mapstruct, cglib, spring 表现最好
- apache 表现最差

基本趋势相当于:

apache -> 10 * hutool -> 28 * spring -> 45 * cglib -> 83 * mapstruct

如果我们需要实现简单的bean拷贝，选择cglib或者spring的是个不错选择

### III. 驼峰下划线拷贝支持

上面的使用都是最基本的使用姿势，属性名 + 类型一致，都有getter/setter方法，我们实际的业务场景中，有一个比较重要的地方，就是下划线与驼峰的转换支持，如果要使用上面的框架，可以怎样适配?

#### 1. cglib 下划线转驼峰

spring cglib封装 与 纯净版的cglib 实现逻辑差别不大，主要是spring里面做了一些缓存，所以表现会相对好一点；为了更加通用，这里以纯净版的cglib进行扩展演示

cglib实现转换的核心逻辑在 `net.sf.cglib.beans.BeanCopier.Generator.generateClass`

```java
public void generateClass(ClassVisitor v) {
    // ... 省略无关代码
    PropertyDescriptor[] getters = ReflectUtils.getBeanGetters(source);
    PropertyDescriptor[] setters = ReflectUtils.getBeanSetters(target);
    
    // 扫描source的所有getter方法，写入到map， key为属性名; 
    // 为了支持驼峰，下划线，我们可以扩展一下这个map，如果属性名为下划线的，额外加一个驼峰的kv进去 
    Map names = new HashMap();
    for (int i = 0; i < getters.length; i++) {
        names.put(getters[i].getName(), getters[i]);
    }
   
    // ...

    for (int i = 0; i < setters.length; i++) {
        PropertyDescriptor setter = setters[i];
        // 这里根据target的属性名，获取source对应的getter方法，同样适配一下，如果下划线格式的获取不到，则改用驼峰的试一下
        PropertyDescriptor getter = (PropertyDescriptor)names.get(setter.getName());
        if (getter != null) {
           // ....
        }
    }
   // ...
}
```

改造逻辑，上面的注释中已经贴出来了，核心实现就比较简单了

提供一个下划线转驼峰的工具了 StrUtil

```java
public class StrUtil {
    private static final char UNDER_LINE = '_';

    /**
     * 下划线转驼峰
     *
     * @param name
     * @return
     */
    public static String toCamelCase(String name) {
        if (null == name || name.length() == 0) {
            return null;
        }

        if (!contains(name, UNDER_LINE)) {
            return name;
        }

        int length = name.length();
        StringBuilder sb = new StringBuilder(length);
        boolean underLineNextChar = false;

        for (int i = 0; i < length; ++i) {
            char c = name.charAt(i);
            if (c == UNDER_LINE) {
                underLineNextChar = true;
            } else if (underLineNextChar) {
                sb.append(Character.toUpperCase(c));
                underLineNextChar = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static boolean contains(String str, char searchChar) {
        return str.indexOf(searchChar) >= 0;
    }
}
```

然后自定义一个 PureCglibBeanCopier, 将之前BeanCopier的代码都拷贝进来，然后改一下上面注释的两个地方 (完整的代码参考项目源码)

```java
public void generateClass(ClassVisitor v) {
    // ... 省略无关代码
    PropertyDescriptor[] setters = ReflectUtils.getBeanSetters(target);
    
    // 扫描source的所有getter方法，写入到map， key为属性名; 
    // 为了支持驼峰，下划线，我们可以扩展一下这个map，如果属性名为下划线的，额外加一个驼峰的kv进去 
    Map<String, PropertyDescriptor> names = buildGetterNameMapper(source)
   
    // ...

    for (int i = 0; i < setters.length; i++) {
        PropertyDescriptor setter = setters[i];
        // 这里根据target的属性名，获取source对应的getter方法，同样适配一下，如果下划线格式的获取不到，则改用驼峰的试一下
        PropertyDescriptor getter = loadSourceGetter(names, setter);
        if (getter != null) {
           // ....
        }
    }
   // ...
}


/**
 * 获取目标的getter方法，支持下划线与驼峰
 *
 * @param source
 * @return
 */
public Map<String, PropertyDescriptor> buildGetterNameMapper(Class source) {
    PropertyDescriptor[] getters = org.springframework.cglib.core.ReflectUtils.getBeanGetters(source);
    Map<String, PropertyDescriptor> names = new HashMap<>(getters.length);
    for (int i = 0; i < getters.length; ++i) {
        String name = getters[i].getName();
        String camelName = StrUtil.toCamelCase(name);
        names.put(name, getters[i]);
        if (!name.equalsIgnoreCase(camelName)) {
            // 支持下划线转驼峰
            names.put(camelName, getters[i]);
        }
    }
    return names;
}

/**
 * 根据target的setter方法，找到source的getter方法，支持下划线与驼峰的转换
 *
 * @param names
 * @param setter
 * @return
 */
public PropertyDescriptor loadSourceGetter(Map<String, PropertyDescriptor> names, PropertyDescriptor setter) {
    String setterName = setter.getName();
    return names.getOrDefault(setterName, names.get(StrUtil.toCamelCase(setterName)));
}
```

使用姿势和之前没有什么区别，就是BeanCopier的创建这里稍稍修改一下即可（BeanCopier可以加缓存，避免频繁的创建）

```java
public <K, T> T copyAndParse(K source, Class<T> target) throws IllegalAccessException, InstantiationException {
    // todo copier 可以缓存起来，避免每次重新创建
    BeanCopier copier = PureCglibBeanCopier.create(source.getClass(), target, false);
    T res = target.newInstance();
    copier.copy(source, res, null);
    return res;
}
```

#### 2. hutool 下划线转驼峰

hutool也支持下划线与驼峰的互转，而且不需要修改源码， 只用我们自己维护一个FieldMapper即可，改动成本较小；而且在map2bean, bean2map时，可以无修改的实现驼峰下划线互转，这一点还是非常很优秀的

```java
/**
 * 驼峰转换
 *
 * @param source
 * @param target
 * @param <K>
 * @param <T>
 * @return
 */
public <K, T> T copyAndParse(K source, Class<T> target) throws Exception {
    T res = target.newInstance();
    // 下划线转驼峰
    BeanUtil.copyProperties(source, res, getCopyOptions(source.getClass()));
    return res;
}

// 缓存CopyOptions（注意这个是HuTool的类，不是Cglib的）

private Map<Class, CopyOptions> cacheMap = new HashMap<>();


private CopyOptions getCopyOptions(Class source) {
    CopyOptions options = cacheMap.get(source);
    if (options == null) {
        // 不加锁，我们认为重复执行不会比并发加锁带来的开销大
        options = CopyOptions.create().setFieldMapping(buildFieldMapper(source));
        cacheMap.put(source, options);
    }
    return options;
}

/**
 * @param source
 * @return
 */
private Map<String, String> buildFieldMapper(Class source) {
    PropertyDescriptor[] properties = ReflectUtils.getBeanProperties(source);
    Map<String, String> map = new HashMap<>();
    for (PropertyDescriptor target : properties) {
        String name = target.getName();
        String camel = StrUtil.toCamelCase(name);
        if (!name.equalsIgnoreCase(camel)) {
            map.put(name, camel);
        }
        String under = StrUtil.toUnderlineCase(name);
        if (!name.equalsIgnoreCase(under)) {
            map.put(name, under);
        }
    }
    return map;
}
```

### 3. mapstruct

最后再介绍一下MapStruct，虽然我们需要手动编码来实现转换，但是好处是性能高啊，既然已经手动编码了，那也就不介意补上下划线和驼峰的转换了

```java
@Mappings({
        @Mapping(target = "userName", source = "user_name"),
        @Mapping(target = "market_price", source = "marketPrice")
})
Target2 copyAndParse(Source source);
```

### 4. 测试

接下来测试一下上面三个是否能正常工作

定义一个Target2，注意它与Source有两个字段不同，分别是 `user_name/userName`, `marketPrice/market_price`

```java
@Data
public class Target2 {
    private Integer id;
    private String userName;
    private Double price;
    private List<Long> ids;
    private BigDecimal market_price;
}

private void camelParse() throws Exception {
    Source s = genSource();
    Target2 cglib = springCglibCopier.copyAndParse(s, Target2.class);
    Target2 cglib2 = pureCglibCopier.copyAndParse(s, Target2.class);
    Target2 hutool = hutoolCopier.copyAndParse(s, Target2.class);
    Target2 map = mapsCopier.copy(s, Target2.class);
    System.out.println("source:" + s + "\nsCglib:" + cglib + "\npCglib:" + cglib2 + "\nhuTool:" + hutool + "\nMapStruct:" + map);
}
```

输出结果如下

```bash
source:Source(id=527180337, user_name=一灰灰Blog, price=7.9, ids=[-2509965589596742300, 5995028777901062972, -1914496225005416077], marketPrice=0.35188996791839599609375)
sCglib:Target2(id=527180337, userName=一灰灰Blog, price=7.9, ids=[-2509965589596742300, 5995028777901062972, -1914496225005416077], market_price=0.35188996791839599609375)
pCglib:Target2(id=527180337, userName=一灰灰Blog, price=7.9, ids=[-2509965589596742300, 5995028777901062972, -1914496225005416077], market_price=0.35188996791839599609375)
huTool:Target2(id=527180337, userName=一灰灰Blog, price=7.9, ids=[-2509965589596742300, 5995028777901062972, -1914496225005416077], market_price=0.35188996791839599609375)
MapStruct:Target2(id=527180337, userName=一灰灰Blog, price=7.9, ids=[-2509965589596742300, 5995028777901062972, -1914496225005416077], market_price=0.35188996791839599609375)
```


性能测试

```java
private <T> void autoCheck2(Class<T> target, int size) throws Exception {
    StopWatch stopWatch = new StopWatch();
    runCopier(stopWatch, "apacheCopier", size, (s) -> apacheCopier.copy(s, target));
    runCopier(stopWatch, "springCglibCopier", size, (s) -> springCglibCopier.copyAndParse(s, target));
    runCopier(stopWatch, "pureCglibCopier", size, (s) -> pureCglibCopier.copyAndParse(s, target));
    runCopier(stopWatch, "hutoolCopier", size, (s) -> hutoolCopier.copyAndParse(s, target));
    runCopier(stopWatch, "springBeanCopier", size, (s) -> springBeanCopier.copy(s, target));
    runCopier(stopWatch, "mapStruct", size, (s) -> mapsCopier.copyAndParse(s,  target));
    System.out.println((size / 10000) + "w -------- cost: " + stopWatch.prettyPrint());
}
```

对比结果如下，虽然cglib, hutool 支持了驼峰,下划线的互转，最终的表现和上面的也没什么太大区别

```bash
1w -------- cost: StopWatch '': running time = 754589100 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
572878100  076%  apacheCopier yihui

017037900  002%  springCglibCopier
031207500  004%  pureCglibCopier
105254600  014%  hutoolCopier
022156300  003%  springBeanCopier
006054700  001%  mapStruct

1w -------- cost: StopWatch '': running time = 601845500 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
494895600  082%  apacheCopier
009014500  001%  springCglibCopier
008998600  001%  pureCglibCopier
067145800  011%  hutoolCopier
016557700  003%  springBeanCopier
005233300  001%  mapStruct

10w -------- cost: StopWatch '': running time = 5543094200 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
4474871900  081%  apacheCopier
089066500  002%  springCglibCopier
090526400  002%  pureCglibCopier
667986400  012%  hutoolCopier
166274800  003%  springBeanCopier
054368200  001%  mapStruct

50w -------- cost: StopWatch '': running time = 27527708400 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
22145604900  080%  apacheCopier
452946700  002%  springCglibCopier
448455700  002%  pureCglibCopier
3365908800  012%  hutoolCopier
843306700  003%  springBeanCopier
271485600  001%  mapStruct
```