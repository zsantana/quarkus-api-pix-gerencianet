#!/bin/bash

mvn clean package

docker build --no-cache -t poc-api-pix-java:v1.0.0 .
#docker push poc-api-pix-java:v1.0.0

#kubectl apply -f minikube-deployment-all.yaml
docker-compose --env-file ./.env up