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

We can then find the Swagger UI under `http://localhost:8080/swagger-ui/`.

Alternatively, we can also build the project and start it using

``` bash
mvn clean package
java -jar target/hausrat-service.jar
```

There's also a `Dockerfile` to run the built JAR within a container.

``` bash
docker build -t "hausrat-service:1.0.0" .
docker run -p 8080:8080 "hausrat-service:1.0.0"
```

## Technologies

The following technologies are used exemplary within this project:

- Spring Boot (MVC, HATEOAS, Actuator, Validation, Data JPA)
- SpringFox Swagger UI
- Lombok
- MapStruct
- Tests are written using
  - Spring Boot Tests with Mockito und AssertJ
  - Cucumber (JUnit)
  - Spock and Groovy (for comparison with Java-based tests)

## Architecture

The project is built based on the idea of [Hexagonal Architectures](https://www.baeldung.com/hexagonal-architecture-ddd-spring). It defines 3 subpackages:
 - domain (the model and the business logic)
 - boundary (the REST service implementation that allows access to the domain)
 - persistence (the implementation of the repositories defined by the domain using Spring Data JPA)
