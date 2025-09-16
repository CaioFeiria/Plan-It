package com.caiofeiria.planit.dtos.usuario;

public class TokenResponseDTO {

    private String token;
    private UsuarioResponseDTO usuario;

    public TokenResponseDTO() {}

    public TokenResponseDTO(String token, UsuarioResponseDTO usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UsuarioResponseDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponseDTO usuario) {
        this.usuario = usuario;
    }
}


