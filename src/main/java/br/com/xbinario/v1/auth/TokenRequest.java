package br.com.xbinario.v1.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@Data
@RegisterForReflection
public class TokenRequest {

    @JsonProperty("grant_type")
    private String grantType;

}
