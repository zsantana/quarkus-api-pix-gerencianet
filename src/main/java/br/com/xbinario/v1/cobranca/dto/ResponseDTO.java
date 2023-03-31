package br.com.xbinario.v1.cobranca.dto;

import java.util.List;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class ResponseDTO {

    private String txid;
    private String revisao;
    private String location;
    private String status;
    private String chave;
    private String solicitacaoPagador;
    
    private Loc loc;
    private Valor valor;
    private CalendarioRet calendario;
    private Devedor devedor;
    private List<Pix> pix;

}