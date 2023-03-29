FROM eclipse-temurin:19-jdk-alpine

COPY . /usr/src/
WORKDIR /usr/src/
RUN apk update && \
    apk add maven
    
RUN mvn clean package

COPY target/service-0.0.1-SNAPSHOT.jar /usr/service.jar
WORKDIR /usr/
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "service.jar"]
