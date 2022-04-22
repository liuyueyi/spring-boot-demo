## 001-properites-env-mvn

### 项目说明

基于mvn的多环境配置选择，主要适用于每个环境下，都有很多配置文件的场景；

此时通过指定maven中profile，激活对应的环境，然后在打包的时候可以将路径下的配置文件都打在一起

```bash
# mvn 指定环境打包操作
mvn clean package -DskipTest=ture -P prod
```

### 博文说明

本项目对应的博文内容为

