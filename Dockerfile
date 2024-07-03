FROM openjdk:17-jdk-alpine as builder
WORKDIR Leanit-server
RUN apk add --no-cache maven
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests
RUN cp target/*.jar Leanit-server.jar && java -Djarmode=layertools -jar Leanit-server.jar extract

FROM openjdk:17-jdk-alpine
WORKDIR Leanit-server
COPY --from=builder Leanit-server/dependencies/ ./
COPY --from=builder Leanit-server/spring-boot-loader/ ./
COPY --from=builder Leanit-server/snapshot-dependencies/ ./
COPY --from=builder Leanit-server/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
EXPOSE 8088
