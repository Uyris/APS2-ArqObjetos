package com.example.Banco.dto;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.DecimalMin;
import java.time.LocalDate;

public class ClienteUpdateDTO {

    private String nome;

    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    @DecimalMin(value = "0.0", inclusive = true, message = "Salário deve ser >= 0")
    private Float salario;

    public ClienteUpdateDTO() {}

    // getters / setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public Float getSalario() { return salario; }
    public void setSalario(Float salario) { this.salario = salario; }
}
