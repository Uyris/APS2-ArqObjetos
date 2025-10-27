package com.example.Banco.ContaCorrente;

import com.example.Banco.Cartao.Cartao;
import com.example.Banco.Cliente.Cliente;
import com.example.Banco.Cliente.ClienteService;
import com.example.Banco.Movimentacao.Movimentacao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ContaCorrenteService {

    private final ContaCorrenteRepository repo;
    private final ClienteService clienteService;

    public ContaCorrenteService(ContaCorrenteRepository repo, ClienteService clienteService) {
        this.repo = repo;
        this.clienteService = clienteService;
    }

    @Transactional(readOnly = true)
    public ContaCorrente getConta(String numero) {
        return repo.findByNumero(numero).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<ContaCorrente> getContas() {
        return repo.findAll();
    }

    @Transactional
    public ContaCorrente salvarConta(ContaCorrente conta) {
        if (conta.getCliente() == null || conta.getCliente().getCpf() == null) {
            throw new RuntimeException("Cliente/CPF obrigatório.");
        }
        Cliente clienteExistente = clienteService.getCliente(conta.getCliente().getCpf());
        if (clienteExistente == null) throw new RuntimeException("CPF inválido.");
        if (repo.existsByNumero(conta.getNumero())) throw new RuntimeException("Número de conta já existe.");
        if (clienteExistente.getContaCorrente() != null) throw new RuntimeException("Cliente já possui uma conta.");

        conta.setCliente(clienteExistente);
        ContaCorrente salvo = repo.save(conta);
        clienteExistente.setContaCorrente(salvo);
        return salvo;
    }

    @Transactional
    public boolean sacar(String numero, Float valor) {
        Optional<ContaCorrente> opt = repo.findByNumero(numero);
        if (opt.isEmpty()) return false;
        ContaCorrente c = opt.get();
        float novo = c.getSaldo() - valor;
        if (novo < 0) return false;
        c.setSaldo(novo);
        Movimentacao mv = new Movimentacao(-valor, "SAQUE");
        c.adicionarMovimentacao(mv);
        repo.save(c);
        return true;
    }

    @Transactional
    public boolean depositar(String numero, Float valor) {
        Optional<ContaCorrente> opt = repo.findByNumero(numero);
        if (opt.isEmpty()) return false;
        ContaCorrente c = opt.get();
        c.setSaldo(c.getSaldo() + valor);
        Movimentacao mv = new Movimentacao(valor, "DEPÓSITO");
        c.adicionarMovimentacao(mv);
        repo.save(c);
        return true;
    }

    @Transactional(readOnly = true)
    public List<Movimentacao> listarMovimentacoes(String numero) {
        Optional<ContaCorrente> opt = repo.findByNumero(numero);
        return opt.map(ContaCorrente::getMovimentacoes).orElse(Collections.emptyList());
    }

    @Transactional
    public void adicionarCartao(ContaCorrente conta, Cartao cartao) {
        ContaCorrente managed = conta;
        if (conta.getId() == null) {
            managed = repo.findByNumero(conta.getNumero()).orElse(conta);
        } else {
            managed = repo.findById(conta.getId()).orElse(conta);
        }
        managed.adicionarCartao(cartao);
        repo.save(managed);
    }
}
