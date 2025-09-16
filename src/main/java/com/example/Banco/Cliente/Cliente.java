package com.example.Banco.Cliente;

import com.example.Banco.ContaCorrente.ContaCorrente;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public class Cliente {
    private String cpf;
    private String nome;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataNascimento;
    private Float salario;

    @JsonIgnore
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

    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

}
