FROM azul/zulu-openjdk:17-latest

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'

COPY --chown=185 target/quarkus-app/lib/ /deployments/lib/
COPY --chown=185 target/quarkus-app/*.jar /deployments/
COPY --chown=185 target/quarkus-app/app/ /deployments/app/
COPY --chown=185 target/quarkus-app/quarkus/ /deployments/quarkus/

# Certificado P12 
COPY --chown=185 certs/homologacao-446582-cert_zanon_conf.p12 /deployments/certs/homologacao-446582-cert_zanon_conf.p12


EXPOSE 8080
USER 185

ENV JAVA_OPTS="-Djavax.net.ssl.trustStore=/deployments/certs/ -Djava.net.preferIPv4Stack=true -Xmx256M -XX:+UseG1GC -Xlog:gc -XX:+ExitOnOutOfMemoryError -Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"

ENTRYPOINT java ${JAVA_OPTS} -jar /deployments/quarkus-run.jar
