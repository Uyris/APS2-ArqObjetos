package com.example.Banco.Cliente;

import com.example.Banco.ContaCorrente.ContaCorrente;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "clientes", indexes = {@Index(name = "idx_cpf", columnList = "cpf", unique = true)})
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String nome;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private Float salario;

    @JsonIgnore
    @OneToOne(mappedBy = "cliente", fetch = FetchType.LAZY)
    private ContaCorrente contaCorrente;

    public Cliente() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public Float getSalario() { return salario; }
    public void setSalario(Float salario) { this.salario = salario; }

    public ContaCorrente getContaCorrente() { return contaCorrente; }
    public void setContaCorrente(ContaCorrente contaCorrente) { this.contaCorrente = contaCorrente; }
}
