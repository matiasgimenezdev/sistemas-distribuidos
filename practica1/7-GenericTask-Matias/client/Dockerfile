FROM eclipse-temurin:19-jdk-alpine
COPY ../task-service/ /usr/task-service/

WORKDIR /usr/task-service/

RUN apk update && \
    apk add maven
    
RUN mvn clean package

COPY target/service.jar /usr/service.jar
WORKDIR /usr/
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "service.jar"]
