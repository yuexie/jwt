
# 基础镜像使用java
FROM java:8
# 作者
MAINTAINER xieyue <xieyue99@gmail.com>
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
VOLUME /tmp 
# 将jar包添加到容器中并更名为app.jar
ADD jwt-0.0.1-SNAPSHOT.jar springboot-docker-jwt.jar
# JVM环境设置
ENV JAVA_OPTS=""
# 运行jar包
RUN bash -c 'touch /springboot-docker-jwt.jar'
# 运行端口
EXPOSE 8010
# ENTRYPOINT里是Docker容器的运行命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/springboot-docker-jwt.jar"]


