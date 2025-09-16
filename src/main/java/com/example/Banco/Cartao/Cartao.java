package com.example.Banco.Cartao;

import com.example.Banco.ContaCorrente.ContaCorrente;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public class Cartao {
    private String numeroCartao;
    private String tipo;
    private LocalDate validade = LocalDate.now().plusYears(10);
    private String status = "ATIVO";
    private String numeroDaConta;

    public boolean isExpired() {
        return validade.isBefore(LocalDate.now());
    }

    public void cancelaCartao() {
        this.status = "CANCELADO";
    }

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

    public String getNumeroContaCorrente() {
        return this.numeroDaConta;
    }

    public void setNumeroDaConta(String numeroDaConta) {
        this.numeroDaConta = numeroDaConta;
    }
}
