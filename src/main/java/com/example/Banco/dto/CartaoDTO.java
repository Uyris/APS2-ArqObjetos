package com.example.Banco.dto;

import com.example.Banco.Cartao.Cartao;

import java.time.LocalDate;

public class CartaoDTO {
    private Integer id;
    private String numeroCartao;
    private String tipo;
    private LocalDate validade;
    private String status;
    private String numeroDaConta;
    private Integer contaCorrenteId;

    public CartaoDTO() {}

    // getters / setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNumeroCartao() { return numeroCartao; }
    public void setNumeroCartao(String numeroCartao) { this.numeroCartao = numeroCartao; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDate getValidade() { return validade; }
    public void setValidade(LocalDate validade) { this.validade = validade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNumeroDaConta() { return numeroDaConta; }
    public void setNumeroDaConta(String numeroDaConta) { this.numeroDaConta = numeroDaConta; }

    public Integer getContaCorrenteId() { return contaCorrenteId; }
    public void setContaCorrenteId(Integer contaCorrenteId) { this.contaCorrenteId = contaCorrenteId; }

    // Mapper: entity -> DTO
    public static CartaoDTO fromEntity(Cartao c) {
        if (c == null) return null;
        CartaoDTO dto = new CartaoDTO();
        dto.setId(c.getId());
        dto.setNumeroCartao(c.getNumeroCartao());
        dto.setTipo(c.getTipo());
        dto.setValidade(c.getValidade());
        dto.setStatus(c.getStatus());
        dto.setNumeroDaConta(c.getNumeroDaConta());
        if (c.getContaCorrente() != null) dto.setContaCorrenteId(c.getContaCorrente().getId());
        return dto;
    }
}
