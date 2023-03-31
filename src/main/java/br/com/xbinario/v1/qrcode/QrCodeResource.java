package br.com.xbinario.v1.qrcode;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
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

import br.com.xbinario.v1.auth.GerencianetService;
import io.quarkus.runtime.annotations.RegisterForReflection;

@Path("/api/v1/qrcode")
@RegisterForReflection
public class QrCodeResource {

    @Inject
    GerencianetService gerencianetService;

    @Inject
    QrCodeService qrCodeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    @RolesAllowed("CONSULTA_QRCODE")
    @Tag(name = "QRCode", description = "Consulta de QRCode")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Consulta Realizada com sucesso."),
        @APIResponse(responseCode = "400", description = "Consulta não encontrada"),
        @APIResponse(responseCode = "422", description = "Erro na conversão do JSON"),
    })
    @Counted(name = "Counted.obterQrCode", 
            tags = {"servico=obterQrCode"},
            description = "Total de Chamadas do enpoint"
            )
    @Timed(name = "Timed.obterQrCode", 
            tags = {"servico=obterQrCode"},
            description = "Tempo medio das requisicoes.", 
            unit = MetricUnits.MILLISECONDS)

    @Retry(maxRetries = 3, delay = 2000)
    @CircuitBreaker(requestVolumeThreshold = 4, failureRatio = 0.5, delay = 5000, successThreshold = 2)
    public Response obterQrCode(@PathParam("id") String id) throws Exception {
        return Response.ok(qrCodeService.obterQrCode(gerencianetService.getToken(), id)).build();
    }
    
}
