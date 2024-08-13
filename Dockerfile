FROM openjdk:17-jdk-alpine as builder
WORKDIR Leanit-server
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} Leanit-server.jar
RUN java -Djarmode=layertools -jar Leanit-server.jar extract

FROM openjdk:17-jdk-alpine
WORKDIR Leanit-server
COPY wait-for-it.sh /usr/local/bin/
COPY --from=builder Leanit-server/dependencies/ ./
COPY --from=builder Leanit-server/spring-boot-loader/ ./
COPY --from=builder Leanit-server/snapshot-dependencies/ ./
COPY --from=builder Leanit-server/application/ ./
RUN chmod +x /usr/local/bin/wait-for-it.sh
ENTRYPOINT ["/usr/local/bin/wait-for-it.sh", "mysqlLeanit:3306", "--timeout=60", "--", "java", "org.springframework.boot.loader.launch.JarLauncher"]
EXPOSE 8088
