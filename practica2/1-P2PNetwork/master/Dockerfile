FROM eclipse-temurin:19-jdk-alpine

COPY /target/master-0.0.1-SNAPSHOT.jar /usr/src/master.jar

WORKDIR /usr/src/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "master.jar"]
