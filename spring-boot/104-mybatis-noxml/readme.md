# SpringBoot系列教程Mybatis整合篇

上一篇博文介绍了SpringBoot整合mybatis的过程，但是xml的方式，总感觉让人有点蛋疼；本文将介绍一种noxml的使用姿势，纯用注解的方式来支持CURD

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

在前一篇的基础上进行扩展，重点在于干掉了xml文件，在DAO接口上通过注解来实现CURD

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

    // 支持主键写回到po

    @Options(useGeneratedKeys = true, keyProperty = "po.id", keyColumn = "id")
    @Insert("insert into money (name, money, is_deleted) values (#{po.name}, #{po.money}, #{po.isDeleted})")
    int savePo(@Param("po") MoneyPo po);

    @Select("select * from money where name=#{name}")
    @Results({@Result(property = "id", column = "id", id = true, jdbcType = JdbcType.INTEGER),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "money", column = "money", jdbcType = JdbcType.INTEGER),
            @Result(property = "isDeleted", column = "is_deleted", jdbcType = JdbcType.TINYINT),
            @Result(property = "createAt", column = "create_at", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "updateAt", column = "update_at", jdbcType = JdbcType.TIMESTAMP)})
    List<MoneyPo> findByName(@Param("name") String name);

    @Update("update money set money=money+#{money} where id=#{id}")
    int addMoney(@Param("id") int id, @Param("money") int money);

    @Delete("delete from money where id = #{id,jdbcType=INTEGER}")
    int delPo(@Param("id") int id);

    @Select("<script> select * from money " +
            "<trim prefix=\"WHERE\" prefixOverrides=\"AND | OR\">" +
            "   <if test=\"id != null\">" +
            "       id = #{id}" +
            "   </if>" +
            "   <if test=\"name != null\">" +
            "       AND name=#{name}" +
            "   </if>" +
            "   <if test=\"money != null\">" +
            "       AND money=#{money}" +
            "   </if>" +
            "</trim>" +
            "</script>")
    @Results({@Result(property = "id", column = "id", id = true, jdbcType = JdbcType.INTEGER),
                @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
                @Result(property = "money", column = "money", jdbcType = JdbcType.INTEGER),
                @Result(property = "isDeleted", column = "is_deleted", jdbcType = JdbcType.TINYINT),
                @Result(property = "createAt", column = "create_at", jdbcType = JdbcType.TIMESTAMP),
                @Result(property = "updateAt", column = "update_at", jdbcType = JdbcType.TIMESTAMP)})
    List<MoneyPo> findByPo(MoneyPo po);
}
```

从mapper的实现上，也可以看出来，通过 `@Insert`, `@Select`, `@Update`, `@Delete` 四个注解来实现CURD，使用上面这种方式时，有几个点需要注意

- insert: 当我们希望插入的主键写回到PO时，可以配置`@Options(useGeneratedKeys = true, keyProperty = "po.id", keyColumn = "id")`
- 动态sql: 在注解中，通过`<script>`来包装动态sql
- @Results 实现`<resultMap>`的映射关系

### 3. 测试

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
        po.setName("mybatis noxml user");
        po.setMoney((long) random.nextInt(12343));
        po.setIsDeleted(0);

        moneyMapper.savePo(po);
        System.out.println("add record: " + po);
        moneyMapper.addMoney(po.getId(), 200);
        System.out.println("after update: " + moneyMapper.findByName(po.getName()));
        moneyMapper.delPo(po.getId());
        System.out.println("after delete: " + moneyMapper.findByName(po.getName()));
    }
}
```

输出结果

![](https://spring.hhui.top/spring-blog/imgs/191230/00.jpg)