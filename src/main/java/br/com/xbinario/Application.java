package br.com.xbinario;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    void onStart(@Observes StartupEvent ev) {       
        // TODO: Incluir rotinas de notificação de inicialização da aplicação        
        logger.info("Iniciando aplicacao Microservico Quarkus [POC-API-PIX-JAVA]...");
    }

    void onStop(@Observes ShutdownEvent ev) {               
        // TODO: Incluir rotinas de notificação de encerramento da aplicação
        logger.info("Interrompendo aplicacao Microservico Quarkus [POC-API-PIX-JAVA]...");
    }
    
}
