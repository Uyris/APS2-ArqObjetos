package com.example.Banco.dto;

import com.example.Banco.Autenticacao.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioCreateDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String senha;

    public UsuarioCreateDTO() {}

    public UsuarioCreateDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    // getters / setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    // Map to entity (use service to persist)
    public Usuario toEntity() {
        Usuario u = new Usuario();
        u.setEmail(this.email);
        u.setSenha(this.senha);
        return u;
    }
}
