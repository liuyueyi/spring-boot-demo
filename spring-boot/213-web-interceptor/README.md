## 213-web-interceptor

### 项目说明

Spring MVC 拦截器，注意它不属于Servlet的三件套，而是Spring生态中的一个环，在具体的web handler执行之前，可用于拦截某些请求

从最终的使用效果来说，它和filter貌似也没有太多的差别；但是在业务处理流程中它是在servlet内部执行的；而filter是在servlet执行之前过滤

重点注意一下，拦截器的注册方式，实现 `WebMvcConfigure`接口，覆盖`addInterrceptors`方法来注册

```java
@SpringBootApplication
public class Application implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/**");
    }
}
```

若我们希望将拦截器声明为bean对象，可以如下操作

```java
@SpringBootApplication
public class Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityInterceptor()).addPathPatterns("/**");
    }
}
```


相关博文:

- [210804-SpringBoot之拦截器Interceptor使用姿势介绍](https://spring.hhui.top/spring-blog/2021/08/04/210804-SpringBoot%E7%B3%BB%E5%88%97Web%E7%AF%87%E4%B9%8B%E6%8B%A6%E6%88%AA%E5%99%A8Interceptor%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF%E4%BB%8B%E7%BB%8D/)
- [211115-SpringBoot之拦截器注入Bean的几种姿势](https://spring.hhui.top/spring-blog/2021/11/15/211115-SpringBoot%E7%B3%BB%E5%88%97%E4%B9%8B%E6%8B%A6%E6%88%AA%E5%99%A8%E6%B3%A8%E5%85%A5Bean%E7%9A%84%E5%87%A0%E7%A7%8D%E5%A7%BF%E5%8A%BF/)