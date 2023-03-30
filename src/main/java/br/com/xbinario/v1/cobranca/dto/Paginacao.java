package br.com.xbinario.v1.cobranca.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class Paginacao {
    private int paginaAtual;
    private int itensPorPagina;
    private int quantidadeDePaginas;
    private int quantidadeTotalDeItens;
}