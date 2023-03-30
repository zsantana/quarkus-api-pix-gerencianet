package br.com.xbinario.token;


import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;


@Path("/api/v1/token")
public class TokenResource {

    @Inject
    TokenGeneratorService service;
    

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Tag(name = "Gerador de Token", description = "Endpoint referente a geração do token para APIs do pagto-digital")
    public Response obterToken(){
            
        return Response.ok(service.getToken()).build();
        
    }


   
    


    
}
