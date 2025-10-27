package com.example.Banco.ContaCorrente;

import com.example.Banco.Autenticacao.AutenticacaoService;
import com.example.Banco.Cliente.Cliente;
import com.example.Banco.Cliente.ClienteService;
import com.example.Banco.Movimentacao.Movimentacao;
import com.example.Banco.dto.ContaCorrenteDTO;
import com.example.Banco.dto.ContaCreateDTO;
import com.example.Banco.dto.MovimentacaoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contas")
public class ContaCorrenteController {

    @Autowired
    private ContaCorrenteService contaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AutenticacaoService autenticacaoService;

    private String extractToken(String header) {
        if (header == null) return null;
        header = header.trim();
        if (header.toLowerCase().startsWith("bearer ")) return header.substring(7).trim();
        return header;
    }

    // listar contas (público)
    @GetMapping
    public ResponseEntity<List<ContaCorrenteDTO>> listarContas() {
        List<ContaCorrenteDTO> dtos = contaService.getContas().stream()
                .map(ContaCorrenteDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // buscar conta (público)
    @GetMapping("/{numero}")
    public ResponseEntity<?> buscarConta(@PathVariable String numero) {
        var conta = contaService.getConta(numero);
        if (conta == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ContaCorrenteDTO.fromEntity(conta));
    }

    // criar conta (precisa token) - recebe ContaCreateDTO com clienteCpf
    @PostMapping
    public ResponseEntity<?> criarConta(@Valid @RequestBody ContaCreateDTO dto,
                                        @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        try {
            autenticacaoService.validarToken(token);

            // valida cliente pelo CPF
            Cliente cliente = clienteService.getCliente(dto.getClienteCpf());
            if (cliente == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente não encontrado para o CPF informado.");
            }

            var entidade = dto.toEntity();
            // associa cliente antes de salvar (service também valida)
            entidade.setCliente(cliente);
            var salvo = contaService.salvarConta(entidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(ContaCorrenteDTO.fromEntity(salvo));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // sacar (precisa token)
    @PostMapping("/{numero}/saque")
    public ResponseEntity<?> sacar(@PathVariable String numero,
                                   @RequestParam Float valor,
                                   @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        try {
            autenticacaoService.validarToken(token);
            boolean ok = contaService.sacar(numero, valor);
            if (ok) return ResponseEntity.ok("Saque realizado com sucesso!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo insuficiente ou conta inexistente.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // depositar (precisa token)
    @PostMapping("/{numero}/deposito")
    public ResponseEntity<?> depositar(@PathVariable String numero,
                                       @RequestParam Float valor,
                                       @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        String token = extractToken(authorizationHeader);
        try {
            autenticacaoService.validarToken(token);
            boolean ok = contaService.depositar(numero, valor);
            if (ok) return ResponseEntity.ok("Depósito realizado com sucesso!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Conta inexistente.");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // listar movimentações (público)
    @GetMapping("/{numero}/movimentacoes")
    public ResponseEntity<List<MovimentacaoDTO>> listarMovimentacoes(@PathVariable String numero) {
        List<Movimentacao> movs = contaService.listarMovimentacoes(numero);
        List<MovimentacaoDTO> dtos = movs.stream()
                .map(MovimentacaoDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
