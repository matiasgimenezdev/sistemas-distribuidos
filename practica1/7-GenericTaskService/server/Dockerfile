FROM eclipse-temurin:19-jdk-alpine

COPY /target/server-0.0.1-SNAPSHOT.jar /usr/src/server.jar

WORKDIR /usr/src/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "server.jar"]
