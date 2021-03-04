400-docker-demo
---

详细博文: [210304-SpringBoot系列整合docker镜像打包](https://spring.hhui.top/spring-blog/2021/03/04/210304-SpringBoot%E7%B3%BB%E5%88%97%E6%95%B4%E5%90%88docker%E9%95%9C%E5%83%8F%E6%89%93%E5%8C%85/)

docker 部署示例文档

打包命令

```bash
mvn clean package docker:build -DskipTests=true
```

启动命令

```bash
# 运行
docker run -i -d --name ddemo -p 8080:8080 -t springboot/400-docker-demo 
# 进入后台
docker exec -it ddemo sh
```

测试

```bash
# 宿主机
curl 'http://127.0.0.1:8080'
```