FROM eclipse-temurin:19-jdk-alpine

COPY /target/sobel-centralized.jar /usr/src/sobel-centralized.jar

WORKDIR /usr/src/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "sobel-centralized.jar"]