package com.example.Banco.Cartao;

import com.example.Banco.ContaCorrente.ContaCorrente;

import java.time.LocalDate;

public class Cartao {
    private String numeroCartao;
    private String tipo;
    private LocalDate validade;
    private String status;
    private ContaCorrente contaCorrente;

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public LocalDate getValidade() {
        return this.validade;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }
}
