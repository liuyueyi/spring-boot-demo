## web-res-gzip
### 项目说明

本项目主要介绍Spring返回结果压缩的使用姿势

1. 压缩配置

```yaml
server:
  compression:
    enabled: true # 开启支持gzip压缩
    min-response-size: 128 # 当响应长度超过128时，才执行压缩 
```
2. 返回JsonObject无效场景

当接口返回的是 application/json 时，上面配置的 min-response-size 无效，全部都走压缩，原因是因为无法计算 content-length，可行的解决方案：

- 设置统一的Filter, 通过`ContentCachingResponseWrapper`包装返回结果 -> 见：Application中的配置

3. 返回压缩的静态资源

```java
public class Application implements WebMvcConfigurer {

    /**
     * 配置返回的静态资源的压缩与缓存方式
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(CacheControl.maxAge(7, TimeUnit.DAYS).cachePrivate())
                .resourceChain(true)
                // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/resource/EncodedResourceResolver.html
                .addResolver(new EncodedResourceResolver())
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
    }
}
```

上面配置了静态资源文件的缓存策略，压缩方式，需要注意 EncodedResourceResolver 需要在 VersionResourceResolver 之前（上面Spring的连接说明）

### 博文说明

本项目对应的博文内容为

[231108-SpringBoot系列之压缩返回结果实例演示 | 一灰灰Blog](http://spring.hhui.top/spring-blog/2023/11/08/231108-SpringBoot%E7%B3%BB%E5%88%97%E4%B9%8B%E5%8E%8B%E7%BC%A9%E8%BF%94%E5%9B%9E%E7%BB%93%E6%9E%9C%E5%AE%9E%E4%BE%8B%E6%BC%94%E7%A4%BA/)