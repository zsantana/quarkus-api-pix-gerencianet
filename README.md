# microservice - API-PIX-JAVA

Modelo de inspsração para integração dos serviços da GerenciaNet para registros de cobrança, qrdcode, pagamentos e outros serviços.





## 💻 Getting started

```bash
# Build 
$ mvn clean package

# Local execution
$ mvn quarkus:dev -Ddebug=false
```


## Getting started Docker
```bash
# Install image from file build_docker_push.sh 
$ ./build_docker_push.sh 

# Started and attaches to containers for a service
$ docker-compose --env-file ./.env up
```


## Getting started Docker (Native Image)
```bash
# Install image from file build_docker_push.sh 
$ ./build_docker_native.sh 

# Started and attaches to containers for a service
$ docker-compose -f docker-compose-native.yml --env-file ./.env up
```


## ✔️ Required
* Maven: 3.8.4
* Java version: 17
* Docker version: 20.10.17
* Docker-compose version: v2.2.2


Docker Image:
* Minikube: v1.29.0
* Keycloak: 19.0.3
* postgres: 13
* jaegertracing/all-in-one: 1
* grafana/grafana: latest
* elasticsearch: 8.4.1
* Kibana: 8.4.1
* azul/zulu-openjdk: 17-latest


## Integrated tools:

Observability:

* smallrye-openapi
* smallrye-metrics
* smallrye-health 
* opentelemetry


Database:
* hibernate-reactive-panache
* reactive-pg-client


Authentication and Security:
* oidc-client
* keycloak-authorization

Other integrations:
* resteasy-reactive-jackson
* lombok
* mapstruct

Unit and Integration testing:
* testcontainers
* keycloak-admin-client
* test-oidc-server
* rest-assured



## Integração com o KeyCloak
Para proteger seus endpoints sobre pode utilizar o keycloak como ferramenta de autorização e autenticação dos tokens de acesso.


### Obtendo um token JWT no kyecloak
Para ter acesso aos endpoists via token JWT, você precisa obter um token via postman.

URL:
http://localhost:7777/auth/realms/NOME-DO-REALM/protocol/openid-connect/token


Body:
* grant_type: client_credentials
* client_id: <client_id>
* client_secret: <client_secret>

OBS:
Por default a validade do token gerado pelo keycloak tem um tem de expiração de 5 minutos, fique atento!




# Autor
Reinaldo Jesus Santana - reinaldojsantana@gmail.com
