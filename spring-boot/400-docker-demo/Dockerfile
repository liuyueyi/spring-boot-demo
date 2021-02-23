
FROM openjdk:8-jdk-alpine
MAINTAINER yihui

VOLUME /tmp
ADD 400-docker-demo-0.0.1-SNAPSHOT.jar app.jar
ADD readme.md readme.md
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
