package br.com.xbinario.v1.cobranca.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class Loc{
    private String criacao;
    private int id;
    private String location;
    private String tipoCob;
}