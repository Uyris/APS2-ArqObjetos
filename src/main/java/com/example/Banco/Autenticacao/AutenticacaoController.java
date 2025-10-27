package com.example.Banco.Autenticacao;

import com.example.Banco.dto.AuthTokenDTO;
import com.example.Banco.dto.UsuarioCreateDTO;
import com.example.Banco.dto.UsuarioDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    // cadastrar usuário (recebe DTO)
    @PostMapping("/usuarios")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody UsuarioCreateDTO dto) {
        try {
            // converte para entidade e delega ao service
            Usuario u = dto.toEntity();
            String msg = autenticacaoService.cadastrarUsuario(u);
            // retorna 201 com mensagem ou poderia retornar UsuarioDTO
            return ResponseEntity.status(HttpStatus.CREATED).body(msg);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    // listar usuários (retorna DTOs — senha não exposta)
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<Usuario> users = autenticacaoService.listarUsuarios();
        List<UsuarioDTO> dtos = users.stream()
                .map(UsuarioDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // login (recebe DTO com email+senha)
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UsuarioCreateDTO dto) {
        try {
            // converte para entidade temporária com email+senha (service usa email+senha)
            Usuario u = dto.toEntity();
            String token = autenticacaoService.login(u);

            // Busca o AuthToken salvo (para retornar metadados) — opcional
            AuthToken at = autenticacaoService.findByTokenEntity(token);
            AuthTokenDTO atDto = AuthTokenDTO.fromEntity(at);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            return ResponseEntity.ok().headers(headers).body(atDto);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    // validar token (GET /auth/validar?token=...)
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
