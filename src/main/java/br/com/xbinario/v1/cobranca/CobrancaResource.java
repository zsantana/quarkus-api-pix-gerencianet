package br.com.xbinario.v1.cobranca;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.xbinario.v1.cobranca.dto.RequestDTO;
import io.quarkus.runtime.annotations.RegisterForReflection;

@Path("/api/v1/cobranca")
@RegisterForReflection
public class CobrancaResource {

    @Inject
    CobrancaService cobrancaService;


    @POST
    @RolesAllowed("EFETUAR_COBRANCA")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Cobrança", description = "Geração de cobranças no GerenciaNet")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Cobrança Realizada com sucesso."),
        @APIResponse(responseCode = "400", description = "Cobrança não encontrada"),
        @APIResponse(responseCode = "422", description = "Erro na conversão do JSON"),
    })
    @Counted(name = "Counted.gerarCobranca", 
            tags = {"servico=gerarCobranca"},
            description = "Total de Chamadas do enpoint"
            )
    @Timed(name = "Timed.gerarCobranca", 
            tags = {"servico=gerarCobranca"},
            description = "Tempo medio das requisicoes.", 
            unit = MetricUnits.MILLISECONDS)

    @Retry(maxRetries = 3, delay = 2000)
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.5, delay = 5000, successThreshold = 2)
    public Response gerarCobranca(RequestDTO requisicaoDTO) throws Exception {
        return Response.ok(cobrancaService.gerarCobranca(requisicaoDTO)).build();
    }



    @GET
    @Path("/all")
    @RolesAllowed("CONSULTA_COBRANCA")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Cobrança", description = "Listagem das cobranças efetuadas no GerenciaNet")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Consulta Realizada com sucesso."),
        @APIResponse(responseCode = "400", description = "Consulta não encontrada"),
        @APIResponse(responseCode = "422", description = "Erro na conversão do JSON"),
    })
    @Counted(name = "Counted.obterCobranca", 
            tags = {"servico=obterCobranca"},
            description = "Total de Chamadas do enpoint"
            )
    @Timed(name = "Timed.obterCobranca", 
            tags = {"servico=obterCobranca"},
            description = "Tempo medio das requisicoes.", 
            unit = MetricUnits.MILLISECONDS)

    @Retry(maxRetries = 3, delay = 2000)
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.5, delay = 5000, successThreshold = 2)
    public Response obterCobranca() throws Exception {
        return Response.ok(cobrancaService.obterListaCobranca()).build();
    }




    @GET
    @Path("/{txid}")
    @RolesAllowed("CONSULTA_COBRANCA")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Cobrança", description = "Pesquisa de cobrança por Id GerenciaNet")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Consulta Realizada com sucesso."),
        @APIResponse(responseCode = "400", description = "Consulta não encontrada"),
        @APIResponse(responseCode = "422", description = "Erro na conversão do JSON"),
    })
    @Counted(name = "Counted.obterCobrancaId", 
            tags = {"servico=obterCobrancaId"},
            description = "Total de Chamadas do enpoint"
            )
    @Timed(name = "Timed.obterCobrancaId", 
            tags = {"servico=obterCobrancaId"},
            description = "Tempo medio das requisicoes.", 
            unit = MetricUnits.MILLISECONDS)

    //@Retry(maxRetries = 3, delay = 2000)
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.5, delay = 5000, successThreshold = 2)
    public Response obterCobrancaId(@PathParam("txid") String txid) throws Exception {
        return Response.ok(cobrancaService.obterCobrancaId(txid)).build();
    }

    
}
