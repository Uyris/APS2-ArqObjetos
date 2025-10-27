package com.example.Banco.dto;

import com.example.Banco.Autenticacao.AuthToken;
import java.time.Instant;

public class AuthTokenDTO {
    private String token;
    private Instant createdAt;
    private Instant expiresAt;
    private Integer usuarioId;

    public AuthTokenDTO() {}

    public AuthTokenDTO(String token, Instant createdAt, Instant expiresAt, Integer usuarioId) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.usuarioId = usuarioId;
    }

    // getters / setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }

    public static AuthTokenDTO fromEntity(AuthToken at) {
        if (at == null) return null;
        Integer uid = (at.getUsuario() == null ? null : at.getUsuario().getId());
        return new AuthTokenDTO(at.getToken(), at.getCreatedAt(), at.getExpiresAt(), uid);
    }
}
