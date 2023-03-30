package br.com.xbinario.v1.cobranca.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class Pix {
    private String endToEndId;
    private String txid;
    private String valor;
    private String chave;
    private String horario;
    private String infoPagador;
}