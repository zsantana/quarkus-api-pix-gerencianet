package br.com.xbinario.v1.auth;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.quarkus.runtime.annotations.RegisterForReflection;

@Path("/oauth/token")
@RegisterForReflection
public class GerencianetResource {
    
    @Inject
    GerencianetService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getToken() throws Exception {
        return Response.ok(service.getTokenObj()).build();
    }

}