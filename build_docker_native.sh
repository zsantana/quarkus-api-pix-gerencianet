#!/bin/bash

mvn clean package -Pnative -Dquarkus.native.container-build=true

docker build -f Dockerfile.native --no-cache -t poc-api-pix-java-native:v1.0.0 .
#docker push poc-api-pix-java:v1.0.0
#docker-compose -f docker-compose-native.yml --env-file ./.env up