FROM openjdk:8-jdk-alpine as builder
MAINTAINER yihui

# 创建工作目录
RUN mkdir -p /home/yihui/workspace/app
# 将jar拷贝过去
COPY /target/400-docker-demo-0.0.1-SNAPSHOT.jar /home/yihui/workspace/app/app.jar
# 将我们预期的文件拷贝过去
COPY /readme.md /home/yihui/workspace/app/readme.md
# 指定工作目录
WORKDIR /home/yihui/workspace/app
# 运行jar
ENTRYPOINT ["java", "-jar", "app.jar"]