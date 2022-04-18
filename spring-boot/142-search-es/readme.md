# es实例demo

## es版本
> https://www.elastic.co/guide/en/elasticsearch/reference/6.8/docker.html

es: 6.8

docker安装命令 `docker pull docker.elastic.co/elasticsearch/elasticsearch:6.8.23`

启动

```
docker run -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:6.8.23
```

## es基本case

权限验证开启

修改配置文件 `config/elasticsearch.yml`，添加下面的配置

```yaml
xpack.security.enabled: true
xpack.security.authc.accept_default_password: false
```

启动es服务

```bash
bin/elasticsearch
```

生成密码

```bash
# 执行完毕之后输入密码， 比如测试的密码都是 test123 (生产环境不要这么干)
bin/elasticsearch-setup-passwords interactive
```

es的交互，主要使用 `Basic Auth` 方式进行身份校验，简单来讲，就是在请求头中，添加

```bash
Authorization: Basic ZWxhc3RpYzp0ZXN0MTIz
```

## 数据准备

```json
POST first-index/_doc
{
  "message": "GET /search HTTP/1.1 200 1070000",
  "user": {
    "id": "kimchy",
    "name": "YiHui"
  },
  "hobby": [
    "java",
    "python"
  ]
}
```


## 系列博文

- [【搜索系列】ES基本项目搭建](https://spring.hhui.top/spring-blog/2022/03/28/220328-SpringBoot%E7%B3%BB%E5%88%97%E4%B9%8BES%E5%9F%BA%E6%9C%AC%E9%A1%B9%E7%9B%AE%E6%90%AD%E5%BB%BA/)
- [【搜索系列】ES文档基本操作CURD实例演示](https://spring.hhui.top/spring-blog/2022/03/31/220331-SpringBoot%E7%B3%BB%E5%88%97%E4%B9%8BES%E6%96%87%E6%A1%A3%E5%9F%BA%E6%9C%AC%E6%93%8D%E4%BD%9CCURD%E5%AE%9E%E4%BE%8B%E6%BC%94%E7%A4%BA/)
- [【搜索系列】ES查询常用实例演示](https://spring.hhui.top/spring-blog/2022/04/18/220418-SpringBoot%E7%B3%BB%E5%88%97%E4%B9%8BES%E6%9F%A5%E8%AF%A2%E7%9A%84%E5%B8%B8%E7%94%A8%E5%AE%9E%E4%BE%8B%E6%BC%94%E7%A4%BA/)