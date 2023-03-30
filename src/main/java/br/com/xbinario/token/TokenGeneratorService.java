package br.com.xbinario.token;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class TokenGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(TokenGeneratorService.class);

    @ConfigProperty(name = "api.autenticacao.jwt.claim.versao")
    String versao;

    @ConfigProperty(name = "api.autenticacao.jwt.claim.issuer")
    String issuer;

    @ConfigProperty(name = "api.autenticacao.jwt.claim.subject")
    String subject;

    @ConfigProperty(name = "api.autenticacao.jwt.claim.audience")
    String audience;

    @ConfigProperty(name = "api.autenticacao.jwt.claim.clientSecret")
    String clientSecret;
   
    public String getToken()  {

        Set<String> grupos = new HashSet<>(Arrays.asList(  "CONSULTA_COBRANCA", 
                                                           "EFETUAR_COBRANCA", 
                                                           "CONSULTA_QRCODE"
                                                        )
                                          );
        String token = "";
        
        try {
            token = Jwt.claim("ver", versao) 
                          .claim("iss", issuer)
                          .claim("sub", subject)
                          .claim("aud", audience)
                          .claim("jti", UUID.randomUUID().toString())
                          .groups(grupos)
                          .issuedAt(Instant.now())
                          .expiresIn(Duration.ofHours(6)) 
                          .sign();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        logger.info("Gerando Token para autenticação: " + token);
        return token;
    }
}
