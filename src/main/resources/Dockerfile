FROM openjdk:11

VOLUME /tmp
# 表示挂载了 /root/tmp 目录到容器中

ARG JAR_FILE
# 构建参数和 ENV 的效果一样，都是设置环境变量。
#所不同的是，ARG 所设置的构建环境的环境变量，在将来容器运行时是不会存在这些环境变量的。
#但是不要因此就使用 ARG 保存密码之类的信息，因为 docker history 还是可以看到所有值的。
RUN echo "Asia/Shanghai" > /etc/timezone

COPY jacob-aide-0.0.2-SNAPSHOT.jar jacob-aide.jar
# 将bootJar 添加到镜像中根目录下 命令为 apprun.jar
EXPOSE 80

ENTRYPOINT ["java","-jar","/jacob-aide.jar"]
# ENTRYPOINT 在容器启动后执行 java 命令来运行程序
