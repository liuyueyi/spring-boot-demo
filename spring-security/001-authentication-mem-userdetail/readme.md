# SpringBoot 整合 SpringSecurity 之基于内存认证（一）

在第一篇的教程中，我们简单的了解了一下SpringSecurity的使用姿势，添加依赖，在`application.yml`文件中加几行配置，就可以实现一个基本的登录认证；

默认的配置只能设置一个账号，那么如果需要多个账号可以怎么支持呢？

本文将介绍一下基于内存的认证方式

<!-- more -->

## I. 内存认证

基于内存保存认证信息的方式，本篇博文中，会介绍两种常见的使用姿势

### 0. 项目配置 

环境配置和前面一致，相关内容可以参考博文: [191223-SpringBoot 整合 SpringSecurity 之起源篇（零）](http://spring.hhui.top/spring-blog/2019/12/23/191223-SpringBoot-%E6%95%B4%E5%90%88-SpringSecurity-%E4%B9%8B%E8%B5%B7%E6%BA%90%E7%AF%87%EF%BC%88%E9%9B%B6%EF%BC%89/)

### 1. WebSecurityConfigurerAdapter

这里主要是借助`SpringSecurity`的配置适配器来处理，下面是一个简单的case

```java
@Configuration
public class SecurityAdapterConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 测试时，可以直接用下面的方式
        //        User.UserBuilder builder = User.withDefaultPasswordEncoder();
        User.UserBuilder builder = User.builder().passwordEncoder(passwordEncoder()::encode);
        auth.inMemoryAuthentication().withUser(builder.username("hui1").password("123456").roles("guest").build());
        auth.inMemoryAuthentication().withUser(builder.username("hui2").password("123456").roles("guest").build());
    }
}
```

主要逻辑在 `configure`这个方法中，但是需要注意，我们额外的设置了密码的加密方式, 当我们不设置这个的时候，实际登录的时候会发现，即便你输入了正确的用户名密码，也会提示失败(欢迎各位大佬实测一下)

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

其次，在创建用户的时候，需要注意的是，除了设置了用户名和密码之外，还给用户加上了一个角色，这个会在后续文章的RBAC（基于角色的授权）中介绍它的作用

### 2. UserDetailsService

这里介绍另外一种方式，在后面的db中保存认证信息时，也会用到；在SpringSecurity的实现中，通过 UserDetailService 这个bean来根据用户名查询对应的用户信息；所以我们只需要实现一个我们自定义的Bean来替换默认的，就可以来实现我们的目标


我们的配置类如下

```java
@Configuration
public class SecurityAutoConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 基于内存的认证方式
     *
     * @param passwordEncoder
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder().passwordEncoder(passwordEncoder::encode);
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("1hui").password("123456").roles("guest").build());
        manager.createUser(users.username("2hui").password("666666").roles("manager").build());
        manager.createUser(users.username("3hui").password("root").roles("admin").build());
        return manager;
    }
}
```


### 3. 测试

上面两种方式，都可以实现在内存中保存认证信息，接下来我们进入实测环节，首先写一个http接口

```java
@RestController
public class IndexRest {

    public String getUser() {
        // 获取用户信息
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    /**
     * @return
     */
    @GetMapping(path = {"/"})
    public String index() {
        return "hello this is index! welcome " + getUser();
    }
}
```

在实际测试时，上面两种case都是ok的，下面的演示过程主要是基于第二种方式给出的示例

![](https://spring.hhui.top/spring-blog/imggs/200111/00.gif)

