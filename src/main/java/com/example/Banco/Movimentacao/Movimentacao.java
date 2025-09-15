package com.example.Banco.Movimentacao;

import com.example.Banco.ContaCorrente.ContaCorrente;

import java.time.LocalDate;

public class Movimentacao {
    private Float valor;
    private String tipo;
    private LocalDate data;
    private ContaCorrente contaCorrente;

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
    }
}
