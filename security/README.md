# Spring Security with Keycloak

## Installation Guide

Install a keycloak server, e.g. by using Docker.

```bash
docker build -t "keycloak-server:1.0.0" .
docker run -p 8180:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=password "keycloak-server:1.0.0"
```

Of course, you can customize the admin's username and password, but setting the credentials on startup is necessary while keycloak is not available on port 8080 on the host system.

## Configuration

You can reach the Keyloak UI by using `http://localhost:8180` and create a realm (e.g. `Hausrat Service Realm`) with users assigned to the roles `customer` and `agent`.
Those roles are declared by this application.

Don't forget to create the Access and Refresh Token by the following request to the Keycloak server:

``` bash
curl -X POST 'http://localhost:8180/auth/realms/Hausrat%20Service%20Realm/protocol/openid-connect/token -i' \
 --header 'Content-Type: application/x-www-form-urlencoded' \
 --data-urlencode 'grant_type=password' \
 --data-urlencode 'client_id=<CLIENT_ID>' \
 --data-urlencode 'client_secret=<CLIENT_SECRET>' \
 --data-urlencode 'username=<USERNAME>' \
 --data-urlencode 'password=<PASSWORD>'

```

## Startup

To enable security within the application, you must activate the `security` profile, e.g. by

``` bash
mvn spring-boot:run -Dspring-boot.run.profiles=security
java -jar target/hausrat-service.jar -Dspring.profiles.active=security
```
