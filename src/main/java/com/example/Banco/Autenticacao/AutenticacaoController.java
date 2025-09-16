package com.example.Banco.Autenticacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    // cadastrar usuário
    @PostMapping("/usuarios")
    public String cadastrar(@RequestBody Usuario usuario) {
        return autenticacaoService.cadastrarUsuario(usuario);
    }

    // listar usuários (público)
    @GetMapping("/usuarios")
    public Collection<Usuario> listarUsuarios() {
        return autenticacaoService.listarUsuarios();
    }

    // login (público)
    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario) {
        return autenticacaoService.login(usuario);
    }
}
