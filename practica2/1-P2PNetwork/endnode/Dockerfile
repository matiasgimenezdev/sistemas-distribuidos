FROM eclipse-temurin:19-jdk-alpine

COPY /target/endnode-0.0.1-SNAPSHOT.jar /usr/src/endnode.jar 
COPY /src/main/resources/ /usr/src/resources/

WORKDIR /usr/src/

RUN mkdir files

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "endnode.jar"]
