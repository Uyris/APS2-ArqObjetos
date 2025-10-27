package com.example.Banco.Autenticacao;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    // cadastrar usuário
    @PostMapping("/usuarios")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
        try {
            String msg = autenticacaoService.cadastrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(msg);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    // listar usuários (cuidado: não retorna senha)
    @GetMapping("/usuarios")
    public Collection<Usuario> listarUsuarios() {
        return autenticacaoService.listarUsuarios();
    }

    // login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        try {
            String token = autenticacaoService.login(usuario);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            return ResponseEntity.ok().headers(headers).body(token);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    // validar token (ex.: GET /auth/validar?token=...)
    @GetMapping("/validar")
    public ResponseEntity<?> validar(@RequestParam("token") String token) {
        try {
            autenticacaoService.validarToken(token);
            return ResponseEntity.ok("Token válido.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    // logout (POST /auth/logout?token=...)
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam("token") String token) {
        try {
            autenticacaoService.logout(token);
            return ResponseEntity.ok("Logout realizado.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
