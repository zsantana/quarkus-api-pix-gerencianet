package br.com.xbinario.v1.cobranca.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class Valor {
    private String original;
}