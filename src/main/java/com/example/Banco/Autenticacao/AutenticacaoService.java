package com.example.Banco.Autenticacao;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

@Service
public class AutenticacaoService {

    private HashMap<String, Usuario> usuarios = new HashMap<>();
    private HashMap<String, Usuario> tokens = new HashMap<>();

    // Cadastro: salva senha já com hash
    public String cadastrarUsuario(Usuario usuario) {
        usuario.setSenha(BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt()));
        usuarios.put(usuario.getEmail(), usuario);
        return "Usuário cadastrado com sucesso!";
    }

    // Listar usuários (cuidado: retorna senha hasheada também)
    public Collection<Usuario> listarUsuarios() {
        return usuarios.values();
    }

    // Login: compara senha digitada com o hash salvo
    public String login(Usuario usuario) {
        Usuario usuarioSalvo = usuarios.get(usuario.getEmail());

        if (usuarioSalvo == null) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        if (BCrypt.checkpw(usuario.getSenha(), usuarioSalvo.getSenha())) {
            var token = UUID.randomUUID().toString();
            tokens.put(token, usuarioSalvo);
            return token;
        }

        throw new RuntimeException("Usuário ou senha inválidos.");
    }

    // Validação de token
    public void validarToken(String token) {
        Usuario usuarioLogado = tokens.get(token);

        if (usuarioLogado == null) {
            throw new RuntimeException("Token inválido.");
        }
    }
}
