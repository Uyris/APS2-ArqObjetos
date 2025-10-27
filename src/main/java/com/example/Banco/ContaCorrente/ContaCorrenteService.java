package com.example.Banco.ContaCorrente;

import com.example.Banco.Cartao.Cartao;
import com.example.Banco.Cliente.Cliente;
import com.example.Banco.Cliente.ClienteRepository;
import com.example.Banco.Movimentacao.Movimentacao;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ContaCorrenteService {

    private final ContaCorrenteRepository repo;
    private final ClienteRepository clienteRepo;

    public ContaCorrenteService(ContaCorrenteRepository repo, ClienteRepository clienteRepo) {
        this.repo = repo;
        this.clienteRepo = clienteRepo;
    }

    public ContaCorrente getConta(String numero) {
        return repo.findByNumero(numero).orElse(null);
    }

    public List<ContaCorrente> getContas() {
        return repo.findAll();
    }

    public ContaCorrente salvarConta(ContaCorrente conta) {
        if (conta.getCliente() == null || conta.getCliente().getCpf() == null) {
            throw new RuntimeException("Cliente/CPF obrigatório.");
        }

        Cliente clienteExistente = clienteRepo.findByCpf(conta.getCliente().getCpf())
                .orElseThrow(() -> new RuntimeException("CPF inválido."));

        conta.setCliente(clienteExistente);
        ContaCorrente salvo = repo.save(conta);

        clienteExistente.setContaCorrente(salvo);
        clienteRepo.save(clienteExistente); // agora precisamos salvar explicitamente

        return salvo;
    }

    public boolean sacar(String numero, Float valor) {
        Optional<ContaCorrente> opt = repo.findByNumero(numero);
        if (opt.isEmpty()) return false;

        ContaCorrente c = opt.get();
        float novoSaldo = c.getSaldo() - valor;
        if (novoSaldo < 0) return false;

        c.setSaldo(novoSaldo);
        Movimentacao mv = new Movimentacao(-valor, "SAQUE");
        c.adicionarMovimentacao(mv);

        repo.save(c);
        return true;
    }

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

    public List<Movimentacao> listarMovimentacoes(String numero) {
        Optional<ContaCorrente> opt = repo.findByNumero(numero);
        return opt.map(ContaCorrente::getMovimentacoes).orElse(Collections.emptyList());
    }

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
