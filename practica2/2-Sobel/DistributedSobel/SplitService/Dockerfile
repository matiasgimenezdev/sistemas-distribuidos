FROM eclipse-temurin:19-jdk-alpine

COPY /target/split-service.jar /usr/src/image-split-service.jar

RUN mkdir /tmp/split/

WORKDIR /usr/src/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "image-split-service.jar"]