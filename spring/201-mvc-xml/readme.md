## 201-mvc-xml

### 项目说明

spring mvc + xml + jetty + war包方式搭建web应用的实例代码

**注意1**

对于需要返回jsp时，需要配置ViewResolver

```xml
<bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/pages/"/>
    <property name="suffix" value=".jsp"/>
</bean>
```

**注意2**

jetty版本与Spring MVC版本不一致时，可能导致本机启动异常，如启动之后报 `Failed startup of context o.e.j.m.p.JettyWebAppContext`，根源是Jetty-servier的版本与jetty-webapp的版本不兼容，需要升级

- [190316-Spring MVC之基于xml配置的web应用构建](http://spring.hhui.top/spring-blog/2019/03/16/190316-Spring-MVC%E4%B9%8B%E5%9F%BA%E4%BA%8Exml%E9%85%8D%E7%BD%AE%E7%9A%84web%E5%BA%94%E7%94%A8%E6%9E%84%E5%BB%BA/)