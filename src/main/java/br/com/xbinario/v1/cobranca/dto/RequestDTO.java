package br.com.xbinario.v1.cobranca.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class RequestDTO {

    private Calendario calendario;
    private Devedor devedor;
    private Valor valor;
    private String chave;
    private String solicitacaoPagador;

}