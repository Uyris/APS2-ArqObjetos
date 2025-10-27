package com.example.Banco.Autenticacao;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "auth_tokens", indexes = {
        @Index(name = "idx_token", columnList = "token", unique = true)
})
public class AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY) // referencia Usuario
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private Instant createdAt;

    private Instant expiresAt;

    public AuthToken() {}

    public AuthToken(String token, Usuario usuario, Instant createdAt, Instant expiresAt) {
        this.token = token;
        this.usuario = usuario;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
}
