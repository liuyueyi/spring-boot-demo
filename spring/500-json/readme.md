json工具类对比
===

基础的转换

- 对象转JsonString
- JsonString转Java bean，转Map/List, 转JsonObject

进阶点：

- 下划线与驼峰方式
- 忽略字段
- 循环引用问题
- 默认值处理（为null的字段，输出为json串时是怎样的？）


注意事项：

- 默认类型，特别是转Map时，数字类型的转换成啥，枚举类型的如何转换
- 类型不匹配时如何处理
- 单引号的json串支持
- key为null时，如何处理


三个框架的对比博文:

- [210715-Json序列化框架对比与最佳实践推荐](https://blog.hhui.top/hexblog/2021/07/15/210715-Json%E5%BA%8F%E5%88%97%E5%8C%96%E6%A1%86%E6%9E%B6%E5%AF%B9%E6%AF%94%E4%B8%8E%E6%9C%80%E4%BD%B3%E5%AE%9E%E8%B7%B5%E6%8E%A8%E8%8D%90/)