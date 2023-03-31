package br.com.xbinario.v1.qrcode;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.xbinario.v1.auth.GerencianetService;
import br.com.xbinario.v1.qrcode.dto.ResponseDTO;

@ApplicationScoped
public class QrCodeService {

    private static final Logger logger = LoggerFactory.getLogger(QrCodeService.class);

    @Inject
    GerencianetService gerencianetService;
   
    @ConfigProperty(name = "gerencianet.qrcode.url")
    String qrcode_url;

    
    public ResponseDTO obterQrCode(String token, String id) throws Exception {
            
        var url = qrcode_url + id + "/qrcode";
        var response = gerencianetService.sendHttpsGetRequest(token, url);

        var objectMapper = new ObjectMapper();
        var responseDTO = objectMapper.readValue(response, ResponseDTO.class);

        logger.info("### responseDTO: {}", response.toString());
        return responseDTO;

    }
    
}
