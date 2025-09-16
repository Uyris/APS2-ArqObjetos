package com.example.Banco.Cliente;

import com.example.Banco.Autenticacao.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AutenticacaoService autenticacaoService;

    // listar todos (público)
    @GetMapping
    public Collection<Cliente> listarClientes() {
        return clienteService.getClientes();
    }

    // buscar cliente por CPF (público)
    @GetMapping("/{cpf}")
    public Cliente buscarCliente(@PathVariable String cpf) {
        return clienteService.getCliente(cpf);
    }

    // criar cliente (precisa de token)
    @PostMapping
    public Cliente salvarCliente(@RequestBody Cliente cliente,
                                 @RequestHeader("Authorization") String token) {
        autenticacaoService.validarToken(token);
        return clienteService.salvarCliente(cliente);
    }

    // atualizar cliente (precisa de token)
    @PutMapping("/{cpf}")
    public Cliente atualizarCliente(@PathVariable String cpf,
                                    @RequestBody Cliente cliente,
                                    @RequestHeader("Authorization") String token) {
        autenticacaoService.validarToken(token);
        cliente.setCpf(cpf);
        return clienteService.atualizarCliente(cliente);
    }

    // excluir cliente (precisa de token)
    @DeleteMapping("/{cpf}")
    public String excluirCliente(@PathVariable String cpf,
                                 @RequestHeader("Authorization") String token) {
        autenticacaoService.validarToken(token);
        clienteService.excluirCliente(cpf);
        return "Cliente removido com sucesso.";
    }
}
