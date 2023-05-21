FROM eclipse-temurin:19-jdk-alpine

COPY /target/assembly-service.jar /usr/src/image-assembly-service.jar

WORKDIR /usr/src/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "image-assembly-service.jar"]