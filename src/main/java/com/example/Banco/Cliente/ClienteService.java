package com.example.Banco.Cliente;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Cliente getCliente(String cpf) {
        return repo.findByCpf(cpf).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Cliente> getClientes() {
        return repo.findAll();
    }

    @Transactional
    public Cliente salvarCliente(Cliente cliente) {
        if (cliente == null || cliente.getCpf() == null || cliente.getCpf().isBlank()) {
            throw new RuntimeException("CPF obrigatório.");
        }
        if (repo.existsByCpf(cliente.getCpf())) {
            throw new RuntimeException("CPF já cadastrado.");
        }
        return repo.save(cliente);
    }

    @Transactional
    public Cliente atualizarCliente(String cpf, Cliente atualizado) {
        Optional<Cliente> opt = repo.findByCpf(cpf);
        if (opt.isEmpty()) throw new RuntimeException("Cliente não encontrado.");

        Cliente existente = opt.get();
        if (atualizado.getNome() != null) existente.setNome(atualizado.getNome());
        if (atualizado.getDataNascimento() != null) existente.setDataNascimento(atualizado.getDataNascimento());
        if (atualizado.getSalario() != null) existente.setSalario(atualizado.getSalario());
        // não permite alterar CPF aqui
        return repo.save(existente);
    }

    @Transactional
    public void excluirCliente(String cpf) {
        if (!repo.existsByCpf(cpf)) throw new RuntimeException("Cliente não encontrado.");
        repo.deleteByCpf(cpf);
    }
}
