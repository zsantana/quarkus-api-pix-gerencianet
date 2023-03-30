package br.com.xbinario.v1.cobranca.dto;

import java.util.List;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class Cob {
    private Calendario calendario;
    private String txid;
    private int revisao;
    private Loc loc;
    private String location;
    private String status;
    private Devedor devedor;
    private Valor valor;
    private String chave;
    private String solicitacaoPagador;
    private List<Pix> pix;
}