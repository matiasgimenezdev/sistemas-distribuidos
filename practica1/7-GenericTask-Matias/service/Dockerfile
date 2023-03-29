FROM eclipse-temurin:19-jdk-alpine

COPY /target/service-0.0.1-SNAPSHOT.jar /usr/src/service.jar

WORKDIR /usr/src/

EXPOSE 5000
ENTRYPOINT ["java", "-jar", "service.jar"]
