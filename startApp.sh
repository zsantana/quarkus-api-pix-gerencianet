#!/bin/bash

mvn clean package

export JAVA_OPTS=" -Xmx128M -XX:+UseZGC -Xlog:gc -XX:+ExitOnOutOfMemoryError"
java $JAVA_OPTS -jar target/quarkus-app/quarkus-run.jar