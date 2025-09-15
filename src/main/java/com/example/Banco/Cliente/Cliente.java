package com.example.Banco.Cliente;

import com.example.Banco.ContaCorrente.ContaCorrente;

import java.time.LocalDate;

public class Cliente {

    private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    private Float salario;
    private ContaCorrente contaCorrente;

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setSalario(Float salario) {
        this.salario = salario;
    }

    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public String getCpf() {
        return cpf;
    }
    public String getNome() {
        return nome;
    }
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public Float getSalario() {
        return salario;
    }
    public ContaCorrente getContaCorrente() {
        return contaCorrente;

    }
}
