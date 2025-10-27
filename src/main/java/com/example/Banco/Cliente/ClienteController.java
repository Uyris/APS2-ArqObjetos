package com.example.Banco.Cliente;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cliente> listar() {
        return service.getClientes();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> buscar(@PathVariable String cpf) {
        Cliente c = service.getCliente(cpf);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente) {
        Cliente salvo = service.salvarCliente(cliente);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Cliente> atualizar(@PathVariable String cpf, @RequestBody Cliente cliente) {
        Cliente atualizado = service.atualizarCliente(cpf, cliente);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletar(@PathVariable String cpf) {
        service.excluirCliente(cpf);
        return ResponseEntity.noContent().build();
    }
}
