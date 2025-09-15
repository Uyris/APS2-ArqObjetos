package com.example.Banco.ContaCorrente;

import com.example.Banco.Cartao.Cartao;
import com.example.Banco.Cliente.Cliente;
import com.example.Banco.Movimentacao.Movimentacao;

import java.util.ArrayList;

public class ContaCorrente {
    private String agencia;
    private String numero;
    private Float saldo;
    private Float limite;
    private Cliente cliente;
    private ArrayList<Cartao> cartoes;
    private ArrayList<Movimentacao> movimentacoes;

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    public void setLimite(Float limite) {
        this.limite = limite;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setCartoes(ArrayList<Cartao> cartoes) {
        this.cartoes = cartoes;
    }

    public void setMovimentacoes(ArrayList<Movimentacao> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    public String getAgencia() {
        return agencia;
    }
    public String getNumero() {
        return numero;
    }

    public Float getSaldo() {
        return saldo;
    }

    public Float getLimite() {
        return limite;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ArrayList<Cartao> getCartoes() {
        return cartoes;
    }

    public ArrayList<Movimentacao> getMovimentacoes() {
        return movimentacoes;
    }

}