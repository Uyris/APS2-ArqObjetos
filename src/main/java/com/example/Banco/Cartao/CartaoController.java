package com.example.Banco.Cartao;

import com.example.Banco.Autenticacao.AutenticacaoService;
import com.example.Banco.ContaCorrente.ContaCorrenteService;
import com.example.Banco.dto.CartaoCreateDTO;
import com.example.Banco.dto.CartaoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @Autowired
    private ContaCorrenteService contaCorrenteService;

    @Autowired
    private AutenticacaoService autenticacaoService;

    // utilitário para extrair token limpando prefixo "Bearer "
    private String extractToken(String header) {
        if (header == null) return null;
        header = header.trim();
        if (header.toLowerCase().startsWith("bearer ")) {
            return header.substring(7).trim();
        }
        return header;
    }

    // listar todos (público) -> retorna DTOs
    @GetMapping
    public ResponseEntity<List<CartaoDTO>> listarTodos() {
        List<CartaoDTO> dtos = cartaoService.listarCartoes()
                .stream()
                .map(CartaoDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // buscar cartão (público) -> retorna DTO
    @GetMapping("/{numero}")
    public ResponseEntity<?> buscarPorNumero(@PathVariable String numero) {
        var cartao = cartaoService.buscarPorNumero(numero);
        if (cartao == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão não encontrado.");
        return ResponseEntity.ok(CartaoDTO.fromEntity(cartao));
    }

    // emitir cartão (precisa token) - recebe DTO de criação
    @PostMapping
    public ResponseEntity<?> emitir(@Valid @RequestBody CartaoCreateDTO dto,
                                    @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        try {
            String token = extractToken(authorizationHeader);
            autenticacaoService.validarToken(token);

            // converte DTO para entidade; service vai associar ContaCorrente e salvar
            var cartaoEntity = dto.toEntity();
            var salvo = cartaoService.emitirCartao(cartaoEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(CartaoDTO.fromEntity(salvo));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // cancelar cartão (precisa token)
    @DeleteMapping("/{numero}")
    public ResponseEntity<?> cancelar(@PathVariable String numero,
                                      @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        try {
            String token = extractToken(authorizationHeader);
            autenticacaoService.validarToken(token);

            boolean ok = cartaoService.cancelar(numero);
            if (ok) return ResponseEntity.ok("Cartão cancelado com sucesso!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão não encontrado.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // verificar status (público)
    @GetMapping("/{numero}/status")
    public ResponseEntity<?> verificarStatus(@PathVariable String numero) {
        boolean ativo = cartaoService.estaAtivo(numero);
        return ResponseEntity.ok(ativo ? "Cartão ativo" : "Cartão inativo ou expirado");
    }
}
