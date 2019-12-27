# SpringBoot系列教程Mybatis整合篇

> MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。MyBatis 可以使用简单的 XML 或注解来配置和映射原生类型、接口和 Java 的 POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

本文将通过实例方式，介绍下如何整合SpringBoot + Mybatis，构建一个支持CRUD的demo工程

## I. 环境

本文使用SpringBoot版本为 `2.2.1.RELEASE`， mybatis版本为`1.3.2`，数据库为mysql 5+

### 1. 项目搭建

推荐是用官方的教程来创建一个SpringBoot项目； 如果直接创建一个maven工程的话，将下面配置内容，拷贝到你的`pom.xml`中

- 主要引入的是`mybatis-spring-boot-starter`，可以减少令人窒息的配置


```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.2.1.RELEASE</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>

<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    <java.version>1.8</java.version>
</properties>

<dependencies>
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>1.3.2</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
</dependencies>

<build>
    <pluginManagement>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </pluginManagement>
</build>
<repositories>
    <repository>
        <id>spring-snapshots</id>
        <name>Spring Snapshots</name>
        <url>https://repo.spring.io/libs-snapshot-local</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
    <repository>
        <id>spring-milestones</id>
        <name>Spring Milestones</name>
        <url>https://repo.spring.io/libs-milestone-local</url>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
    </repository>
    <repository>
        <id>spring-releases</id>
        <name>Spring Releases</name>
        <url>https://repo.spring.io/libs-release-local</url>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
    </repository>
</repositories>
```

### 2. 配置信息

在 `application.yml` 配置文件中，加一下db的相关配置

```yml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/story?useUnicode=true&characterEncoding=UTF-8&useSSL=false
    username: root
    password:
```

接下来准备一个测试表(依然借用之前db操作系列博文中的表结构)，用于后续的CURD；表结果信息如下

```sql
DROP TABLE IF EXISTS `money`;

CREATE TABLE `money` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `money` int(26) NOT NULL DEFAULT '0' COMMENT '有多少钱',
  `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
  `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
``` 


## II. 实例整合

本文将介绍一下传统的xml使用姿势，手动的添加`PO`, `DAO`, `Mapper.xml`；至于Generator来自动生成的case，后面通过图文的方式进行介绍

### 1. PO 

创建表对应的PO对象: `MoneyPo`

```java
@Data
public class MoneyPo {
    private Integer id;

    private String name;

    private Long money;

    private Integer isDeleted;

    private Timestamp createAt;

    private Timestamp updateAt;
}
```

### 2. DAO接口

表的操作接口，下面简单的写了四个接口，分别对应CRUID四种操作

```java
@Mapper
public interface MoneyMapper {

    int savePo(@Param("po") MoneyPo po);

    List<MoneyPo> findByName(@Param("name") String name);

    int addMoney(@Param("id") int id, @Param("money") int money);

    int delPo(@Param("id") int id);
}
```

重点观察下上面接口的两个注解

- `@Mapper`：声明这个为mybatis的dao接口，spring扫描到它之后，会自动生成对应的代理类
    - 使用这个注解之后，可以不再启动类上加上`@MapperScan`； 当然加上`@MapperScan`之后，也可以不用这个注解
- `@Param`： 主要传递到xml文件中，方便参数绑定


这里简单说一下几种常见的参数传递方式

#### a. 单参数传递

如果只有一个基本类型的参数，可以直接使用参数名的使用方式

```java
MoneyPo findById(int id);
```

对应的xml文件如下（先忽略include 与 resultMap）, 可以直接用参数名

```xml
<select id="findById" parameterType="java.lang.Integer" resultMap="BaseResultMap">
    select
    <include refid="money_po"/>
    from money where id=#{id}
</select>
```


#### b. 多参数默认传递

当接口定义有多个参数时，就不能直接使用参数名了，使用 arg0, arg1... (或者 param1, param2...)

实例如下

```java
List<MoneyPo> findByNameAndMoney(String name, Integer money);
```

对应的xml

```xml
<select id="findByNameAndMoney" resultMap="BaseResultMap">
    select
    <include refid="money_po"/>
--         from money where name=#{param1} and money=#{param2}
    from money where name=#{arg0} and money=#{arg1}
</select>
```

#### c. @Param方式

就是上面case中的方式，xml中的参数就是注解的value；就不给演示了（后续的xml中可以看到使用姿势）

#### d. Map传参

接口定义一个Map<String, Object> 类型的参数，然后在xml中，就可以使用key的值来表明具体选中的是哪一个参数

```java
List<MoneyPo> findByMap(Map<String, Object> map);
```

对应的xml如下，关于标签的用法主要是mybatis的相关知识点，这里不详细展开

```xml
<select id="findByMap" resultMap="BaseResultMap">
    select
    <include refid="money_po"/>
    from money
    <trim prefix="WHERE" prefixOverrides="AND | OR">
        <if test="id != null">
            id = #{id}
        </if>
        <if test="name != null">
            AND name=#{name}
        </if>
        <if test="money != null">
            AND money=#{money}
        </if>
    </trim>
</select>
```

#### e. POJO传参

参数为一个POJO对象，实际使用中，通过成员名来确定具体的参数

```java
List<MoneyPo> findByPo(MoneyPo po);
```

对应的xml如下，需要添加参数`parameterType` 指定POJO的类型

**此外请额外注意下面的参数使用姿势和后面`savePo`接口对应的实现中参数的引用区别**

```xml
<select id="findByPo" parameterType="com.git.hui.boot.mybatis.entity.MoneyPo" resultMap="BaseResultMap">
        select
    <include refid="money_po"/>
    from money
    <trim prefix="WHERE" prefixOverrides="AND | OR">
        <if test="id != null">
            id = #{id}
        </if>
        <if test="name != null">
            AND name=#{name}
        </if>
        <if test="money != null">
            AND money=#{money}
        </if>
    </trim>
</select>
```

### 3. xml实现

上面的Mapper接口中定义接口，具体的实现需要放在xml文件中，在我们的实例case中，xml文件放在 `resources/sqlmapper`目录下

文件名为`money-mapper.xml`， 没有什么特别的要求

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.git.hui.boot.mybatis.mapper.MoneyMapper">

    <resultMap id="BaseResultMap" type="com.git.hui.boot.mybatis.entity.MoneyPo">
        <id column="id" property="id" jdbcType="INTEGER"/>
        <result column="name" property="name" jdbcType="VARCHAR"/>
        <result column="money" property="money" jdbcType="INTEGER"/>
        <result column="is_deleted" property="isDeleted" jdbcType="TINYINT"/>
        <result column="create_at" property="createAt" jdbcType="TIMESTAMP"/>
        <result column="update_at" property="updateAt" jdbcType="TIMESTAMP"/>
    </resultMap>
    <sql id="money_po">
      id, name, money, is_deleted, create_at, update_at
    </sql>

    <insert id="savePo" parameterType="com.git.hui.boot.mybatis.entity.MoneyPo" useGeneratedKeys="true"
            keyProperty="po.id">
      INSERT INTO `money` (`name`, `money`, `is_deleted`)
      VALUES
	  (#{po.name}, #{po.money}, #{po.isDeleted});
    </insert>

    <update id="addMoney" parameterType="java.util.Map">
        update money set money=money+#{money} where id=#{id}
    </update>

    <delete id="delPo" parameterType="java.lang.Integer">
        delete from money where id = #{id,jdbcType=INTEGER}
    </delete>

    <select id="findByName" parameterType="java.lang.String" resultMap="BaseResultMap">
        select
        <include refid="money_po"/>
        from money where name=#{name}
    </select>
</mapper>
```

在上面的xml文件中，除了四个接口对应的实现之外，还定义了一个`resultMap` 和 `sql`

- sql 标签定义通用的sql语句片段，通过`<include refid="xxx"/>`方式引入，避免写重复代码
- resultMap: 定义表中数据与POJO成员的映射关系，比如将下划线的命名映射成驼峰

### 4. mybatis配置

上面基本上完成了整合工作的99%, 但是还有一个问题没有解决，mapper接口如何与xml文件关联起来？

- xml文件中的mapper标签的namespace指定了具体的mapper接口, 表明这个xml文件对应的这个mapper

但是对于spring而言，并不是所有的xml文件都会被扫描的，毕竟你又不是 `web.xml` 这么有名（为什么web.xml就这么特殊呢😝, 欢迎查看我的[Spring MVC之基于xml配置的web应用构建](http://spring.hhui.top/spring-blog/2019/03/16/190316-Spring-MVC%E4%B9%8B%E5%9F%BA%E4%BA%8Exml%E9%85%8D%E7%BD%AE%E7%9A%84web%E5%BA%94%E7%94%A8%E6%9E%84%E5%BB%BA/)）

为了解决xml配置扫描问题，请在 `application.yml` 文件中添加下面这一行配置

```yml
mybatis:
  mapper-locations: classpath:sqlmapper/*.xml
```

### 5. 测试

接下来简单测试一下上面的四个接口，看是否可以正常工作

启动类

```java
@SpringBootApplication
public class Application {

    public Application(MoneyRepository repository) {
        repository.testMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

测试类

```java
@Repository
public class MoneyRepository {
    @Autowired
    private MoneyMapper moneyMapper;

    private Random random = new Random();

    public void testMapper() {
        MoneyPo po = new MoneyPo();
        po.setName("mybatis user");
        po.setMoney((long) random.nextInt(12343));
        po.setIsDeleted(0);

        moneyMapper.savePo(po);
        System.out.println("add record: " + po);
        moneyMapper.addMoney(po.getId(), 200);
        System.out.println("query: " + moneyMapper.findByName(po.getName()));
        moneyMapper.delPo(po.getId());
        System.out.println("after delete: " + moneyMapper.findByName(po.getName()));
    }
}
```

输出结果

![](https://spring.hhui.top/spring-blog/imgs/191227/00.jpg)