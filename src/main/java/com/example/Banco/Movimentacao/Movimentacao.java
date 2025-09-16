package com.example.Banco.Movimentacao;

import com.example.Banco.ContaCorrente.ContaCorrente;

import java.time.LocalDate;

public class Movimentacao {
    private Float valor;
    private String tipo; // "SAQUE" ou "DEPÓSITO"
    private LocalDate data = LocalDate.now();

    public Movimentacao(float valor, String saque) {
        this.valor = valor;
        this.tipo = saque;
        this.data = LocalDate.now();
    }

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
}
