# es实例demo

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