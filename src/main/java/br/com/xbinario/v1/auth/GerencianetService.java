package br.com.xbinario.v1.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class GerencianetService {

    private static final Logger logger = LoggerFactory.getLogger(GerencianetService.class);

    String msgErro = "Erro de autenticação";

    @ConfigProperty(name = "gerencianet.clientId")
    String clientId;

    @ConfigProperty(name = "gerencianet.clientSecret")
    String clientSecret;

    @ConfigProperty(name = "gerencianet.keyStorePath")
    String keyStorePath;

    @ConfigProperty(name = "gerencianet.auth.url")
    String url;

    @ConfigProperty(name = "gerencianet.cobranca.url")
    String cobranca_url;


    public TokenResponse getTokenObj() throws Exception {

        String basicAuth = Base64.getEncoder().encodeToString((clientId+':'+ clientSecret).getBytes());
        HttpsURLConnection conn = getHttpConnection(url, basicAuth, "POST", "Basic");
        String body = "{\"grant_type\": \"client_credentials\"}";

        OutputStream os = conn.getOutputStream();
        os.write(body.getBytes());
        os.flush();     

        if (conn.getResponseCode() != 200){
            logger.error("Authentication failed: " + conn.getResponseMessage());
            throw new WebApplicationException(Response.status(conn.getResponseCode()).entity(msgErro).build());
        }

        String response = new BufferedReader(new InputStreamReader(conn.getInputStream()))
                                                            .lines()
                                                            .collect(Collectors.joining());
        conn.disconnect();                                                    

        var objectMapper = new ObjectMapper();
        TokenResponse tokenResponse  = objectMapper.readValue(response, TokenResponse.class);

        
        return tokenResponse;
    }


    public String getToken() throws Exception {

        String basicAuth = Base64.getEncoder().encodeToString((clientId+':'+ clientSecret).getBytes());
        HttpsURLConnection conn = getHttpConnection(url, basicAuth, "POST", "Basic");
        String body = "{\"grant_type\": \"client_credentials\"}";

        OutputStream os = conn.getOutputStream();
        os.write(body.getBytes());
        os.flush();     

        if (conn.getResponseCode() != 200){
            logger.error("Authentication failed: " + conn.getResponseMessage());
            throw new WebApplicationException(Response.status(conn.getResponseCode()).entity(msgErro).build());
        }

        String response = new BufferedReader(new InputStreamReader(conn.getInputStream()))
                                                            .lines()
                                                            .collect(Collectors.joining());

        JsonObject jsonObject = new JsonObject(response.toString());
        String token = jsonObject.getString("access_token");

        conn.disconnect();
        return token;
    }

    public String sendHttpsPostRequest(String token, String urlStr, String requestBody) throws Exception {

        logger.info("### POST: " + requestBody);
        HttpsURLConnection conn = getHttpConnection(urlStr, token, "POST", "Bearer");

        OutputStream os = conn.getOutputStream();
        os.write(requestBody.getBytes());
        os.flush();

        String response = sendRequest(conn);
        conn.disconnect();
        
        return response;
    }

    

    public String sendHttpsGetRequest(String token, String urlStr) throws Exception {

        logger.info("### GET: " + urlStr);
        HttpsURLConnection conn = getHttpConnection(urlStr, token, "GET", "Bearer");

        String response = sendRequest(conn);
        conn.disconnect();
        
        return response;
    }


    private String sendRequest(HttpsURLConnection conn) throws IOException {

        logger.info("Efetuando consulta ...");
        
        String response = "";

        try {
            
            response = getResponse(conn);
            conn.disconnect();

        } catch (Exception e) {

            response = new BufferedReader(new InputStreamReader(conn.getErrorStream()))
                                            .lines()
                                            .collect(Collectors.joining());
            
            String msgErro = ("MSG ERRO: " + response.toString());
            logger.error(msgErro +  " - " + e.getMessage());

            throw new WebApplicationException(Response.status(conn.getResponseCode()).entity(msgErro).build());
        }
            
        return response;
    }


    
    private HttpsURLConnection getHttpConnection(String urlStr, String token, String methode, String typeAuth)
            throws MalformedURLException, IOException, ProtocolException, Exception {

        logger.info("### urlStr: "  + urlStr);
        logger.info("### token: "  + token);
        logger.info("### methode: "  + methode);
        logger.info("### typeAuth: "  + typeAuth);
        logger.info("### keyStorePath: " + keyStorePath);

        SSLSocketFactory sslsocketfactory = getSSLSocketFactory();

        URL url = new URL(urlStr);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod(methode);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", typeAuth + " " + token);
        conn.setSSLSocketFactory(sslsocketfactory);

        return conn;
    }


    private String getResponse(HttpsURLConnection conn) throws IOException {
        String response = new BufferedReader(new InputStreamReader(conn.getInputStream()))
                                                .lines()
                                                .collect(Collectors.joining());
        return response;
    }


    private SSLSocketFactory getSSLSocketFactory() throws Exception {
        System.setProperty("javax.net.ssl.keyStore", keyStorePath);
        return (SSLSocketFactory) SSLSocketFactory.getDefault();
    }

}
