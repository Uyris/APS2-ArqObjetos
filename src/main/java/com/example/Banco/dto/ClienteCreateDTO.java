package com.example.Banco.dto;

import com.example.Banco.Cliente.Cliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public class ClienteCreateDTO {

    @NotBlank(message = "CPF é obrigatório")
    // opcional: regex para CPF (ex.: "\\d{11}") ou formato com pontos/traço
    private String cpf;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    @NotNull(message = "Salário é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "Salário deve ser >= 0")
    private Float salario;

    public ClienteCreateDTO() {}

    // getters / setters
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public Float getSalario() { return salario; }
    public void setSalario(Float salario) { this.salario = salario; }

    // Mapper: DTO -> entity (não associa conta aqui)
    public Cliente toEntity() {
        Cliente c = new Cliente();
        c.setCpf(this.cpf);
        c.setNome(this.nome);
        c.setDataNascimento(this.dataNascimento);
        c.setSalario(this.salario);
        return c;
    }
}
