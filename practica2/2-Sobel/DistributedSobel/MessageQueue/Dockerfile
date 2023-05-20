FROM rabbitmq:alpine

RUN apk update && apk add --no-cache openjdk17

COPY /target/message-queue.jar /usr/src/message-queue.jar
WORKDIR /usr/src/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "message-queue.jar"]
