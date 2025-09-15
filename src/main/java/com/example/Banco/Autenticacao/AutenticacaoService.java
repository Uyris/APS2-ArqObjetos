package com.example.Banco.Autenticacao;

import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

@Service
public class AutenticacaoService {

    private HashMap<String, Usuario> usuarios = new  HashMap<>();

    private HashMap<String, Usuario> tokens = new  HashMap<>();

    public Usuario cadastrarUsuario(Usuario usuario) {
        usuarios.put(usuario.getEmail(), usuario);
        return usuario;
    }

    public Collection<Usuario> listarUsuarios() {
        return usuarios.values();
    }

    public String login(Usuario usuario) {

        Usuario usuarioLogado = usuarios.get(usuario.getEmail());

        if (usuarioLogado.getSenha().equals(usuario.getSenha())) {
            var token = UUID.randomUUID().toString();
            tokens.put(token, usuario);
            return token;
        }

        throw new RuntimeException("Usuario ou Senha inválidos.");
    }

    public void validarToken(String token) {

        Usuario usuarioLogado = tokens.get(token);

        if (usuarioLogado == null) {
            throw new RuntimeException("Token inválido.");
        }
    }
}
