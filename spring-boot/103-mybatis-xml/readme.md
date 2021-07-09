## 103-mybatis-xml

springboot 整合 mybatis + xml配置方式实例demo，对应的博文:

- [191227-SpringBoot系列教程Mybatis-xml整合篇](http://spring.hhui.top/spring-blog/2019/12/27/191227-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8BMybatis-xml%E6%95%B4%E5%90%88%E7%AF%87/)


Mapper接口与sql文件映射的四种姿势

- 默认：在resource资源目录下，xml文件的目录层级与Mapper接口的包层级完全一致，且xml文件名与mapper接口文件名也完全一致
    - 如mapper接口： `com.git.hui.boot.mybatis.mapper.MoneyMapper`
    - 对应的xml文件:  `com/git/hui/boot/mybatis/mapper/MoneyMapper.xml`
- springboot配置参数:
    - application.yml配置文件中，指定 `mybatis.mapper-locations=classpath:sqlmapper/*.xml`
- mybatis-config配置文件
    - 这种姿势常见于非SpringBoot项目集成mybatis，通常将mybatis的相关配置放在 `mybatis-config.xml` 文件中
    - 首先在配置文件中，指定加载参数 `mybatis.config-location=classpath:mybatis-config.xml`
    - 然后指定映射器 ` <mappers><mapper resource="sqlmapper/money-mapper.xml"/></mappers>`
- SqlSessionFactory指定
    - 直接在SqlSessionFactory中指定即可Mapper文件

```java
// 设置mybatis的xml所在位置，这里使用mybatis注解方式，没有配置xml文件
bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapping/*.xml"));
```

---

类型别名:

- 在 `mybatis-config.xml` 文件中指定，这样默认的别名为首字母小写的case, 如 `MoneyPo` 的别名就是 `moneyPo`

```xml
<typeAliases>
    <!-- 类型别名   -->
    <package name="com.git.hui.boot.mybatis.entity"/>
</typeAliases>
```

- 依然是在上面的xml文件中，指定别名

```xml
<typeAliases>
    <typeAlias alias="MoneyPo" type="com.git.hui.boot.mybatis.entity.MoneyPo"/>
</typeAliases>
```
- 使用注解 `@Alias`，放在PO对象上
