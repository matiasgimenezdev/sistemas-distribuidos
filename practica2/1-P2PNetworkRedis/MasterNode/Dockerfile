FROM eclipse-temurin:19-jdk-alpine

COPY /target/master-node.jar /usr/src/master-node.jar

WORKDIR /usr/src/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "master-node.jar"]