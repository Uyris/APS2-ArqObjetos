package com.example.Banco.Cliente;

import com.example.Banco.dto.ClienteCreateDTO;
import com.example.Banco.dto.ClienteDTO;
import com.example.Banco.dto.ClienteUpdateDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    // listar todos -> retorna DTOs
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        List<ClienteDTO> dtos = service.getClientes().stream()
                .map(ClienteDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // buscar por cpf -> retorna DTO
    @GetMapping("/{cpf}")
    public ResponseEntity<?> buscar(@PathVariable String cpf) {
        Cliente c = service.getCliente(cpf);
        if (c == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ClienteDTO.fromEntity(c));
    }

    // criar -> recebe DTO validado
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody ClienteCreateDTO dto) {
        try {
            Cliente entidade = dto.toEntity();
            Cliente salvo = service.salvarCliente(entidade);
            return ResponseEntity.status(201).body(ClienteDTO.fromEntity(salvo));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // atualizar -> recebe update DTO (não altera CPF)
    @PutMapping("/{cpf}")
    public ResponseEntity<?> atualizar(@PathVariable String cpf, @Valid @RequestBody ClienteUpdateDTO dto) {
        try {
            // Converte updateDTO em uma entidade parcial para passar ao service
            Cliente parcial = new Cliente();
            parcial.setNome(dto.getNome());
            parcial.setDataNascimento(dto.getDataNascimento());
            parcial.setSalario(dto.getSalario());

            Cliente atualizado = service.atualizarCliente(cpf, parcial);
            return ResponseEntity.ok(ClienteDTO.fromEntity(atualizado));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // deletar
    @DeleteMapping("/{cpf}")
    public ResponseEntity<?> deletar(@PathVariable String cpf) {
        try {
            service.excluirCliente(cpf);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
