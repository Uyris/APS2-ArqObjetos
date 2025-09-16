package com.example.Banco.ContaCorrente;

import com.example.Banco.Movimentacao.Movimentacao;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContaCorrenteService {

    private HashMap<String, ContaCorrente> contas = new HashMap<>();

    public ContaCorrente getConta(String numero){
        return contas.get(numero);
    }

    public Collection<ContaCorrente> getContas() {
        return contas.values();
    }

    public ContaCorrente salvarConta(ContaCorrente conta){
        contas.put(conta.getNumero(), conta);
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
}
