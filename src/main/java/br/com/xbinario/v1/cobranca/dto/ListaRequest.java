package br.com.xbinario.v1.cobranca.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class ListaRequest {

    private Map<String, Object> parametros;
    private List<Cob> cobs;

    @Data
    public static class Parametros {
        private LocalDateTime inicio;
        private LocalDateTime fim;
        private Paginacao paginacao;
    }

}