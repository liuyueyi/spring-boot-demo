## 005-config-selector

### 项目说明

我们知道可以通过`@ConditionOnXxx`来决定一个配置类是否可以加载，那么假设有这么个应用场景

- 有一个Print的抽象接口，有多个实现，如输出到控制台的ConsolePrint, 输出到文件的 FilePrint, 输出到db的 DbPrint
- 我们在实际使用的时候，根据用户的选择，使用其中的一个具体实现

针对上面的case，当然也可以使用`@ConditionOnExpression`来实现，除此之外推荐一种更优雅的选择注入方式`ImportSelector`


### 相关博文


- [191214-SpringBoot系列教程自动配置选择生效](http://spring.hhui.top/spring-blog/2019/12/14/191214-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E8%87%AA%E5%8A%A8%E9%85%8D%E7%BD%AE%E9%80%89%E6%8B%A9%E7%94%9F%E6%95%88/)