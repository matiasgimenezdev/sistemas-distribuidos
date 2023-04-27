FROM eclipse-temurin:19-jdk-alpine

COPY /target/end-node.jar /usr/src/end-node.jar

WORKDIR /usr/src/

EXPOSE 9000
ENTRYPOINT ["java", "-jar", "end-node.jar"]