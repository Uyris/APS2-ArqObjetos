package com.example.Banco.ContaCorrente;

import com.example.Banco.Cartao.Cartao;
import com.example.Banco.Cliente.ClienteService;
import com.example.Banco.Movimentacao.Movimentacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContaCorrenteService {

    private HashMap<String, ContaCorrente> contas = new HashMap<>();

    @Autowired
    private ClienteService clienteService;

    public ContaCorrente getConta(String numero){
        return contas.get(numero);
    }

    public Collection<ContaCorrente> getContas() {
        return contas.values();
    }

    public ContaCorrente salvarConta(ContaCorrente conta){

        if (clienteService.getCliente(conta.getCliente().getCpf()) == null) {
            throw new RuntimeException("CPF Inválido.");
        }

        if (contas.get(conta.getCliente().getCpf()) != null){
            throw new RuntimeException("Não é possível criar duas contas correntes para o mesmo cliente.");
        }

        contas.put(conta.getNumero(),  conta);
        clienteService.getCliente(conta.getCliente().getCpf()).setContaCorrente(conta);
        return conta;
    }

    public boolean sacar(String numero, Float valor){
        ContaCorrente conta = contas.get(numero);
        if (conta != null && conta.getSaldo() >= valor) {
            conta.setSaldo(conta.getSaldo() - valor);
            conta.getMovimentacoes().add(new Movimentacao(-valor, "SAQUE"));
            return true;
        }
        return false;
    }

    public boolean depositar(String numero, Float valor){
        ContaCorrente conta = contas.get(numero);
        if (conta != null) {
            conta.setSaldo(conta.getSaldo() + valor);
            conta.getMovimentacoes().add(new Movimentacao(valor, "DEPÓSITO"));
            return true;
        }
        return false;
    }

    public List<Movimentacao> listarMovimentacoes(String numero){
        ContaCorrente conta = contas.get(numero);
        return (conta != null) ? conta.getMovimentacoes() : Collections.emptyList();
    }

    public void adicionarCartao(ContaCorrente conta ,Cartao cartao){
        conta.getCartoes().add(cartao);
    }
}
