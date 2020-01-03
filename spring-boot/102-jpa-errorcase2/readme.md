# SpringBoot系列JPA错误姿势之Entity映射

本篇为JPA错误使用姿势第二篇，java的POJO类与数据库表结构的映射关系，除了驼峰命名映射为下划线之外，还会有什么别的坑么？

<!-- more -->

## I. 映射问题

### 1. 项目基本配置

首先搭建基本的springboot + jpa项目， 我们使用的springboot版本为`2.2.1.RELEASE`，mysql版本5+

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

项目配置文件`application.properties`

```properties
## DataSource
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/story?useUnicode=true&characterEncoding=UTF-8&useSSL=false
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=
spring.jpa.database=MYSQL
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jackson.serialization.indent_output=true
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```

表结构

```sql
CREATE TABLE `meta_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group` varchar(32) NOT NULL DEFAULT '' COMMENT '分组',
  `profile` varchar(32) NOT NULL DEFAULT '' COMMENT 'profile 目前用在应用环境 取值 dev/test/pro',
  `desc` varchar(64) NOT NULL DEFAULT '' COMMENT '解释说明',
  `deleted` int(4) NOT NULL DEFAULT '0' COMMENT '0表示有效 1表示无效',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `group_profile` (`group`,`profile`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='业务配置分组表';
```

### 2. 错误case

java变量命名推荐的是驼峰命名方式，因此与数据库中字段的下划线方式需要关联映射，通过jpa的相关知识学习，我们知道可以使用`@Column`注解来处理，所以有下面这种写法

```java
@Data
@Entity
@Table(name = "meta_group")
public class ErrorMetaGroupPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "group")
    private String group;

    @Column(name = "profile")
    private String profile;

    @Column(name = "desc")
    private String desc;

    @Column(name = "deleted")
    private Integer deleted;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;
}
```

从命名上就可以看出上面这种case是错误的，那么到底是什么问题呢？


先写一个对应的Repository来实测一下

```java
public interface ErrorGroupJPARepository extends JpaRepository<ErrorMetaGroupPo, Integer> {
}
```

测试代码

```java
@Component
public class GroupManager {
    @Autowired
    private ErrorGroupJPARepository errorGroupJPARepository;

    public void test() {
        String group = UUID.randomUUID().toString().substring(0, 4);
        String profile = "dev";
        String desc = "测试jpa异常case!";
        try {
            int id = addGroup1(group, profile, desc);
            System.out.println("add1: " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer addGroup1(String group, String profile, String desc) {
        ErrorMetaGroupPo jpa = new ErrorMetaGroupPo();
        jpa.setGroup("add1: " + group);
        jpa.setDesc(desc);
        jpa.setProfile(profile);
        jpa.setDeleted(0);
        Timestamp timestamp = Timestamp.from(Instant.now());
        jpa.setCreateTime(timestamp);
        jpa.setUpdateTime(timestamp);
        ErrorMetaGroupPo res = errorGroupJPARepository.save(jpa);
        return res.getId();
    }
}
```

![](https://spring.hhui.top/spring-blog/imgs/200103/00.jpg)


从输出结果来看，提示的是sql异常，why?

- group,desc 为关键字，拼sql的时候需要用反引号包裹起来

### 3. 正确姿势一

第一种正确使用姿势，直接在`@column`的name中，添加反引号包裹起来

```java
@Data
@Entity
@Table(name = "meta_group")
public class MetaGroupPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`group`")
    private String group;

    @Column(name = "`profile`")
    private String profile;

    @Column(name = "`desc`")
    private String desc;

    @Column(name = "`deleted`")
    private Integer deleted;

    @Column(name = "`create_time`")
    private Timestamp createTime;

    @Column(name = "`update_time`")
    private Timestamp updateTime;
}
```

### 4. 正确姿势二

除了上面的case之外，还有另外一种通用的方式，实现自定义的`PhysicalNamingStrategy`，实现字段映射

比如我们自定义`JpaNamingStrategyStandardImpl`继承自默认的`PhysicalNamingStrategyStandardImpl`策略，然后在字段名中，对于没有引号的包裹的字段名主动添加一个反引号

```java
public class JpaNamingStrategyStandardImpl extends PhysicalNamingStrategyStandardImpl {
    @Setter
    private static int mode = 0;

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        if (mode == 1) {
            if (name.isQuoted()) {
                return name;
            } else {
                return Identifier.toIdentifier("`" + name.getText() + "`", true);
            }
        } else {
            return name;
        }
    }
}
```

注意使用上面的映射策略，需要修改配置文件(`application.properties`)

```properties
spring.jpa.hibernate.naming.physical-strategy=com.git.hui.boot.jpacase.strategy.JpaNamingStrategyStandardImpl
```

测试case

```java
@SpringBootApplication
public class Application {
    public Application(GroupManager groupManager) {
        groupManager.test();
    }

    public static void main(String[] args) {
        JpaNamingStrategyStandardImpl.setMode(1);
        SpringApplication.run(Application.class, args);
    }
}

@Component
public class GroupManager {
    @Autowired
    private ErrorGroupJPARepository errorGroupJPARepository;

    @Autowired
    private GroupJPARepository groupJPARepository;


    public void test() {
        String group = UUID.randomUUID().toString().substring(0, 4);
        String profile = "dev";
        String desc = "测试jpa异常case!";
        try {
            int id = addGroup1(group, profile, desc);
            System.out.println("add1: " + errorGroupJPARepository.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int id2 = addGroup2(group, profile, desc);
            System.out.println("add2: " + groupJPARepository.findById(id2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer addGroup1(String group, String profile, String desc) {
        ErrorMetaGroupPo jpa = new ErrorMetaGroupPo();
        jpa.setGroup("add1: " + group);
        jpa.setDesc(desc);
        jpa.setProfile(profile);
        jpa.setDeleted(0);
        Timestamp timestamp = Timestamp.from(Instant.now());
        jpa.setCreateTime(timestamp);
        jpa.setUpdateTime(timestamp);
        ErrorMetaGroupPo res = errorGroupJPARepository.save(jpa);
        return res.getId();
    }

    public Integer addGroup2(String group, String profile, String desc) {
        MetaGroupPO jpa = new MetaGroupPO();
        jpa.setGroup("add2: " + group);
        jpa.setDesc(desc);
        jpa.setProfile(profile);
        jpa.setDeleted(0);
        Timestamp timestamp = Timestamp.from(Instant.now());
        jpa.setCreateTime(timestamp);
        jpa.setUpdateTime(timestamp);
        MetaGroupPO res = groupJPARepository.save(jpa);
        return res.getId();
    }
}
```

执行之后输出:

![](https://spring.hhui.top/spring-blog/imgs/200103/01.jpg)