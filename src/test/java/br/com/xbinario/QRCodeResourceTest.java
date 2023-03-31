package br.com.xbinario;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import wiremock.com.fasterxml.jackson.core.JsonProcessingException;
import wiremock.com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testng.annotations.BeforeTest;

import br.com.xbinario.v1.cobranca.dto.Calendario;
import br.com.xbinario.v1.cobranca.dto.Devedor;
import br.com.xbinario.v1.cobranca.dto.RequestDTO;
import br.com.xbinario.v1.cobranca.dto.ResponseDTO;
import br.com.xbinario.v1.cobranca.dto.Valor;

import static io.restassured.RestAssured.given;

import java.util.UUID;

import io.restassured.response.Response;

@QuarkusTest
@TestSecurity(authorizationEnabled = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class QRCodeResourceTest {

    private String locationId = "";


    @BeforeEach
    public void setup() throws JsonProcessingException  {
          
        RequestDTO req = new RequestDTO();
        Calendario c = new Calendario();
        Devedor d = new Devedor();
        Valor v = new Valor();

        c.setExpiracao(3600);
        d.setCpf("12345678909");
        d.setNome(UUID.randomUUID().toString());
        v.setOriginal("0.01");

        req.setCalendario (c);
        req.setDevedor(d);
        req.setValor(v);
        req.setChave("71cdf9ba-c695-4e3c-b010-abb521a3f1be");
        req.setSolicitacaoPagador(UUID.randomUUID().toString());

        Response response = given()
                            .contentType("application/json")
                            .body(new ObjectMapper().writeValueAsString(req))
                            .when()
                                .post("/api/v1/cobranca")
                            .then()
                                .statusCode(200)
                            .extract()
                            .response();

        var responseBody = response.getBody().asString();
        var responseDTO = new ObjectMapper().readValue(responseBody, ResponseDTO.class);

        System.out.println("################# LOC_ID: " + responseDTO.getLoc().getId());
        System.out.println("################# LOC: " + responseDTO.toString());

        locationId = String.valueOf( responseDTO.getLoc().getId());
    }


    @Test
    @Order(2)
    public void testObterQRCode() {

        given()
          .when().get("/api/v1/qrcode/" + locationId)
          .then()
             .statusCode(200)
             ;
    }

    
}
