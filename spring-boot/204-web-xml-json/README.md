## web-xml
### 项目说明

实例演示如何设置返回数据类型，同一个接口支持根据不同的使用方式，返回json、xml格式数据

核心知识点：

**方式一**

接口上定义produce, 如 `@GetMapping(path = "p2", produces = {"application/xml", "application/json"})`

注意produces属性值是有序的，即先定义的优先级更高；当一个请求可以同时接受xml/json格式数据时，上面这个定义会确保这个接口现有返回xml格式数据


**方式二**

借助标准的请求头accept，控制希望返回的数据类型；但是需要注意的时，使用这种方式时，要求后端不能设置`ContentNegotiationConfigurer.ignoreAcceptHeader(true)`


在实际使用这种方式的时候，客户端需要额外注意，Accept请求头中定义的MediaType的顺序，是优于后端定义的produces顺序的，因此用户需要将自己实际希望接受的数据类型放在前面，或者干脆就只设置一个


**方式三**

借助`ContentNegotiationConfigurer`实现通过请求参数来决定返回类型，常见的配置方式形如

```java
configurer.favorParameter(true)
        // 禁用accept协商方式，即不关心前端传的accept值
      //                .ignoreAcceptHeader(true)
        // 哪个放在前面，哪个的优先级就高； 当上面这个accept未禁用时，若请求传的accept不能覆盖下面两种，则会出现406错误
        .defaultContentType(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
        // 根据传参mediaType来决定返回样式
        .parameterName("mediaType")
        // 当acceptHeader未禁用时，accept的值与mediaType传参的值不一致时，以mediaType的传值为准
        // mediaType值可以不传，为空也行，但是不能是json/xml之外的其他值
        .mediaType("json", MediaType.APPLICATION_JSON)
        .mediaType("xml", MediaType.APPLICATION_XML);
```



即添加这个设置之后，最终的表现为：

1. 请求参数指定的返回类型，优先级最高，返回指定参数对应的类型
2. 没有指定参数时，选择defaultContentType定义的默认返回类型与接口 `produce`中支持的求交集，优先级则按照defaultContentType中定义的顺序来选择
3. 没有指定参数时，若此时还有accept请求头，则请求头中定义顺序的优先级高于 defaultContentType， 高于 produce

注意注意：当配置中忽略了AcceptHeader时，`.ignoreAcceptHeader(true)`，上面第三条作废

### 博文说明

本项目对应的博文内容为

* [220817-springboot系列之定义接口返回类型的几种方式 | 一灰灰Learning](https://hhui.top/spring-web/02.response/16.220817-springboot%E7%B3%BB%E5%88%97%E4%B9%8B%E5%AE%9A%E4%B9%89%E6%8E%A5%E5%8F%A3%E8%BF%94%E5%9B%9E%E7%B1%BB%E5%9E%8B%E7%9A%84%E5%87%A0%E7%A7%8D%E6%96%B9%E5%BC%8F/)
