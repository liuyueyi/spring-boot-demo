## 003-self-config-binder

自定义的配置加载，并支持动态刷新

- 基于Binder来实现，将配置注入拥有对应注解的配置类上
- 基于ApplicationContextInitializer来实现，注入自定义的配置源，然后适用于所有的@Value、配置使用的场景
