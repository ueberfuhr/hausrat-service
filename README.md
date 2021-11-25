# Household Insurance (REST service implementation with Spring Boot)

## Requirements

To build and run, we need
 - JDK 11+
 - Maven

## First Steps

We can clone this repository and start the service by the following command:

``` bash
mvn spring-boot:run
```

Alternatively, we can also build the project and start it using

``` bash
mvn clean package
java -jar target/hausrat-service.jar
```

There's also a `Dockerfile` to run the built JAR within a container.

## Technologies

The following technologies are used exemplary within this project:

- Spring Boot (MVC, HATEOAS, Actuator, Validation, Data JPA)
- SpringFox Swagger UI
- Lombok
- MapStruct
- Tests are written using
  - Cucumber (JUnit)
  - Spock and Groovy
