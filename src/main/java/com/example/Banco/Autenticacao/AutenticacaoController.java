package com.example.Banco.Autenticacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/usuarios")
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @PostMapping
    public Usuario cadastrarUsuario(@RequestBody Usuario usuario) {
        return autenticacaoService.cadastrarUsuario(usuario);
    }

    @GetMapping
    public Collection<Usuario> listarUsuarios() {
        return autenticacaoService.listarUsuarios();
    }

    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario) {
        return autenticacaoService.login(usuario);
    }
}
