package br.com.xbinario.v1.cobranca;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.xbinario.v1.auth.GerencianetService;
import br.com.xbinario.v1.cobranca.dto.ListaRequest;
import br.com.xbinario.v1.cobranca.dto.RequestDTO;
import br.com.xbinario.v1.cobranca.dto.ResponseDTO;


@ApplicationScoped
public class CobrancaService {

    private static final Logger logger = LoggerFactory.getLogger(CobrancaService.class);

    String msgErro = "Erro de autenticação";


    @Inject
    GerencianetService gerencianetService;
   
    @ConfigProperty(name = "gerencianet.cobranca.url")
    String cobranca_url;

    

    public ResponseDTO gerarCobranca(RequestDTO dto) throws Exception {
            
        var requestBody = new ObjectMapper().writeValueAsString(dto);
        var response = gerencianetService.sendHttpsPostRequest(gerencianetService.getToken(), cobranca_url, requestBody);

        var objectMapper = new ObjectMapper();
        var responseDTO = objectMapper.readValue(response, ResponseDTO.class);

        logger.info("### responseDTO: {}", response.toString());
        return responseDTO;

    }


    public ListaRequest obterListaCobranca() throws Exception {

        var _url = cobranca_url + "?inicio=2023-03-29T08:01:35Z&fim=2023-03-30T20:10:00Z";
        var token = gerencianetService.getToken();

        logger.info("_url: " + _url);
        logger.info("TOKEN: " + token);

        var response = gerencianetService.sendHttpsGetRequest(token, _url);

        var objectMapper = new ObjectMapper();
        var responseDTO = objectMapper.readValue(response, ListaRequest.class);

        logger.info("### responseDTO: {}", responseDTO.toString());
        return responseDTO;

    }


    public ResponseDTO obterCobrancaId(String txId) throws Exception {
        
        var _url = cobranca_url + "/" + txId;
        var response = gerencianetService.sendHttpsGetRequest(gerencianetService.getToken(), _url);

        var objectMapper = new ObjectMapper();
        var responseDTO = objectMapper.readValue(response, ResponseDTO.class);

        logger.info("### responseDTO: {}", responseDTO.toString());
        return responseDTO;

    }
    
}
