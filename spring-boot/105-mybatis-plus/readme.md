# SpringBoot系列教程MybatisPlus整合篇

前面介绍了SpringBoot整合Mybatis 实现db的增删改查操作，分别给出了xml和注解两种实现mapper接口的方式；虽然注解方式干掉了xml文件，但是使用起来并不优雅，本文将介绍mybats-plus的使用case，简化常规的CRUD操作

## I. 环境

本文使用SpringBoot版本为 `2.2.1.RELEASE`， mybatis-plus版本为`3.2.0`，数据库为mysql 5+

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
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>3.2.0</version>
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

mybatis-plus与mybatis的使用姿势有一些区别，下面为不借助`generator`直接手撸代码的case

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

表的操作接口，与mybatis不同的是这个接口继承`BaseMapper`之后，就自带了单表的CURD操作接口了，基本上不需要定义额外的接口，就可以实现db交互


```java
public interface MoneyMapper extends BaseMapper<MoneyPo> {
}
```

- 注意`BaseMapper`的参数为表对应的PO对象

### 3. 测试

上面完成之后，整合过程基本上就完了，没错，就这么简单，接下来我们进入测试环节

首先是启动类，我们加上了`@MapperScan`注解，这样在DAO接口上就不需要添加`@Mapper`注解了

```java
@SpringBootApplication
@MapperScan("com.git.hui.boot.mybatisplus.mapper")
public class Application {

    public Application(MoneyRepository repository) {
        repository.testMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

关于测试case，下面会演示CRUD四种基本的操作case，因为本文重点不是介绍mybatis-plus的用法，对于下面代码有疑问的可以查看官方文档: [https://mp.baomidou.com/guide/](https://mp.baomidou.com/guide/)

```java
@Component
public class MoneyRepository {
    @Autowired
    private MoneyMapper moneyMapper;

    private Random random = new Random();

    public void testDemo() {
        MoneyPo po = new MoneyPo();
        po.setName("mybatis plus user");
        po.setMoney((long) random.nextInt(12343));
        po.setIsDeleted(0);

        // 添加一条数据
        moneyMapper.insert(po);

        // 查询
        List<MoneyPo> list =
                moneyMapper.selectList(new QueryWrapper<MoneyPo>().lambda().eq(MoneyPo::getName, po.getName()));
        System.out.println("after insert: " + list);

        // 修改
        po.setMoney(po.getMoney() + 300);
        moneyMapper.updateById(po);
        System.out.println("after update: " + moneyMapper.selectById(po.getId()));

        // 删除
        moneyMapper.deleteById(po.getId());

        // 查询
        Map<String, Object> queryMap = new HashMap<>(2);
        queryMap.put("name", po.getName());
        System.out.println("after delete: " + moneyMapper.selectByMap(queryMap));
    }
}
```

输出结果

![](https://spring.hhui.top/spring-blog/imgs/191231/00.jpg)