601-actuator
---

[spring-boot/actuator.html](https://springdoc.cn/spring-boot/actuator.html#)

应用监控示例

1. 默认提供的端点是无法通过外网访问的，只有 `/actuator/health` 可以直接外网访问，其他的都需要安全管控
    - 通过配置 `management.endpoints.web.exposure.include` 指定哪些端点可以公开访问
2. 默认情况下，除 shutdown 外的所有端点都被启用
   要配置一个端点的启用，请使用其 `management.endpoint.<id>.enabled` 属性
3. CSRF保护默认开启
4. CORS支持在默认情况下是禁用的，通过添加配置开启

```
management.endpoints.web.cors.allowed-origins=https://example.com
management.endpoints.web.cors.allowed-methods=GET,POST
```

5. 可以使用健康信息来检查你运行的应用程序的状态, health 端点暴露的信息取决于 `management.endpoint.health.show-details`
   和 `management.endpoint.health.show-components` 属性

| 值               | 说明                                                               | 
|-----------------|------------------------------------------------------------------| 
| never           | 细节从不显示。     <- 默认值                                               |
| when-authorized | 细节只显示给授权用户。  授权的角色可以通过使用`management.endpoint.health.roles` 进行配置。 |
| always          | 详情显示给所有用户。                                                       |

6. 自定义的监控信息，可以通过实现 `HealthIndicator` 并注册到Spring Bean来完成

```java
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MyHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        int errorCode = check();
        if (errorCode != 0) {
            return Health.down().withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    private int check() {
        // perform some specific health check
        return 0;
    }

}
```

> 一个给定的 HealthIndicator 的标识符（ID）是没有 HealthIndicator 后缀的Bean的名字，如果它存在的话。 在前面的例子中，健康信息可以在一个名为
> my 的条目中找到。

健康指标通常是通过HTTP调用的，需要在任何连接超时之前做出响应。 如果任何健康指标的响应时间超过10秒，Spring Boot将记录一条警告信息。
如果你想配置这个阈值，你可以使用 management.endpoint.health.logging.slow-indicator-threshold 属性。

除了Spring Boot预定义的 Status 类型外，Health 可以返回代表新系统状态的自定义 Status。 在这种情况下，你还需要提供
StatusAggregator 接口的自定义实现，或者你必须通过使用 management.endpoint.health.status.order 配置属性来配置默认实现。

例如，假设在你的一个 HealthIndicator 实现中使用了一个代码为 FATAL 的新 Status。 为了配置严重性顺序，在你的应用程序属性中添加以下属性。

```Properties
management.endpoint.health.status.order=fatal,down,out-of-service,unknown,up
```

响应中的HTTP状态代码反映了整体健康状态。 默认情况下，OUT_OF_SERVICE 和 DOWN 映射到503。 任何未映射的健康状态，包括
UP，都映射为200。 如果你通过HTTP访问健康端点，你可能还想注册自定义状态映射。 配置自定义映射会禁用 DOWN 和 OUT_OF_SERVICE
的默认映射。 如果你想保留默认映射，你必须明确地配置它们，以及任何自定义映射。 例如，下面的属性将 FATAL 映射为503（服务不可用），并保留了
DOWN 和 OUT_OF_SERVICE 的默认映射。

```properties
management.endpoint.health.status.http-mapping.down=503
management.endpoint.health.status.http-mapping.fatal=503
management.endpoint.health.status.http-mapping.out-of-service=503
```

内置状态的默认状态映射如下

| Status         | 	Mapping                  |
|----------------|---------------------------|
| DOWN           | SERVICE_UNAVAILABLE (503) |
| OUT_OF_SERVICE | SERVICE_UNAVAILABLE (503) |
| UP             | 默认情况下没有映射，所以HTTP状态为 200。  |
| UNKNOWN        | 默认情况下没有映射，所以HTTP状态为 200。  |

