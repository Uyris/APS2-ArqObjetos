package com.example.Banco.dto;

import com.example.Banco.Autenticacao.Usuario;

public class UsuarioDTO {
    private Integer id;
    private String email;

    public UsuarioDTO() {}

    public UsuarioDTO(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

    // getters / setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // mapper
    public static UsuarioDTO fromEntity(Usuario u) {
        if (u == null) return null;
        return new UsuarioDTO(u.getId(), u.getEmail());
    }
}
