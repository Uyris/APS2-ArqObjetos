package com.example.Banco.dto;

import com.example.Banco.Cliente.Cliente;

import java.time.LocalDate;

public class ClienteDTO {
    private Integer id;
    private String cpf;
    private String nome;
    private LocalDate dataNascimento;
    private Float salario;
    private Integer contaCorrenteId;

    public ClienteDTO() {}

    // getters / setters
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

    public Integer getContaCorrenteId() { return contaCorrenteId; }
    public void setContaCorrenteId(Integer contaCorrenteId) { this.contaCorrenteId = contaCorrenteId; }

    // Mapper: entity -> DTO
    public static ClienteDTO fromEntity(Cliente c) {
        if (c == null) return null;
        ClienteDTO dto = new ClienteDTO();
        dto.setId(c.getId());
        dto.setCpf(c.getCpf());
        dto.setNome(c.getNome());
        dto.setDataNascimento(c.getDataNascimento());
        dto.setSalario(c.getSalario());
        if (c.getContaCorrente() != null) dto.setContaCorrenteId(c.getContaCorrente().getId());
        return dto;
    }
}
