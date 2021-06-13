FROM openjdk:11-jdk
COPY ./target/hausrat-service.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
