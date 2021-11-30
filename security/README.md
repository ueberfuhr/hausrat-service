# Spring Security with Keycloak

## Installation Guide

Install a keycloak server, e.g. by using Docker.

```bash
docker build -t "keycloak-server:1.0.0" .
docker run -p 8180:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=password "keycloak-server:1.0.0"
```

Of course, you can customize the admin's username and password, but setting the credentials on startup is necessary while keycloak is not available on port 8080 on the host system.

You can reach the Keyloak UI by using `http://localhost:8180`.
