## 009-distribute-scheduler-task

### 项目说明

分布式定时任务演示实例，单机版mock方案，控制单机的多个定时任务，只允许一个执行

- AOP方案： 
    - 使用之前，需要将 ScheduleDemo 的private方法，修改为public或者protected或删掉
    - 启动类 Application 中开启`@EnableScheduling` 注释 `@EnableDistributeScheduling`
- ScheduledAnnotationBeanPostProcessor 扩展方案
    - 当前默认的是这种方式，可以直接进行测试

### 博文说明

本项目对应的博文内容为

- [200412-SpringBoot系列教程之实现一个简单的分布式定时任务](https://spring.hhui.top/spring-blog/2020/04/12/200412-SpringBoot%E7%B3%BB%E5%88%97%E6%95%99%E7%A8%8B%E4%B9%8B%E5%AE%9E%E7%8E%B0%E4%B8%80%E4%B8%AA%E7%AE%80%E5%8D%95%E7%9A%84%E5%88%86%E5%B8%83%E5%BC%8F%E5%AE%9A%E6%97%B6%E4%BB%BB%E5%8A%A1/)