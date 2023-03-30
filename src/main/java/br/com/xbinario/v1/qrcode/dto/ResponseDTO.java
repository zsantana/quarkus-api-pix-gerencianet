package br.com.xbinario.v1.qrcode.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class ResponseDTO {

    private String qrcode;
    private String imagemQrcode;
    private String linkVisualizacao;
    
}
