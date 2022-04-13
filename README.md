# Household Insurance (REST service implementation with Spring Boot)
[![Docker Image CI](https://github.com/ueberfuhr/hausrat-service/actions/workflows/docker-image.yml/badge.svg)](https://github.com/ueberfuhr/hausrat-service/actions/workflows/docker-image.yml)

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
(You should be redirected automatically to the Swagger UI when using `http://localhost:8080`.)

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

- Spring Boot (MVC, HATEOAS, Actuator, Validation, Data JPA, Security)
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

## Reactive Stack

The API is implemented with synchronous calls, the API calls are executed within the default _one-thread-per-request_-ThreadModel. All invocations are synchronous.

Additionally, we have also a reactive stack implementation (in the `reactive` branch) using
 - Spring WebFlux
 - Spring Data R2DBC

## Security

The service runs by default without any security constrains, but we can optionally start it with the `secure` profile.
Then, invoking the API requires authentication and uses a role-based authorization:

 - Role `customer` is allowed to read and create insurance calculations
 - Role `agent` is allowed to create, modify and delete products

### Implementation Hints

 - Spring Security with Keycloak (see [separate instructions](security/README.md))
 - Global Method Security is configured that allows to use annotation based authorization.
   - see [Configuration](src/main/java/de/sample/hausrat/config/security/KeycloakWebSecurityConfig.java)
   - see [Sample Controller](src/main/java/de/sample/hausrat/boundary/ProductController.java)
 - OpenAPI contains extended descriptions to describe secured operations (🔒 symbol and the allowed roles)
   - see [OpenAPI Extension](src/main/java/de/sample/hausrat/config/security/KeycloakOpenAPIConfig.java)
 - Because SpringFox 3.0.0 includes an older version of Swagger UI (`3.26.0`), that does not support OpenID Connect Discovery (`3.38.0+`), we overwrite the bundled Swagger UI by a newer version (`4.1.2`)
   - see the [Swagger UI Files](src/main/resources/META-INF/resources/webjars/springfox-swagger-ui)
