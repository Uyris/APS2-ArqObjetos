package com.example.Banco.dto;

import com.example.Banco.Movimentacao.Movimentacao;
import java.time.LocalDate;

public class MovimentacaoDTO {
    private Integer id;
    private Float valor;
    private String tipo;
    private LocalDate data;
    private Integer contaCorrenteId;

    public MovimentacaoDTO() {}

    // getters / setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Float getValor() { return valor; }
    public void setValor(Float valor) { this.valor = valor; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public Integer getContaCorrenteId() { return contaCorrenteId; }
    public void setContaCorrenteId(Integer contaCorrenteId) { this.contaCorrenteId = contaCorrenteId; }

    public static MovimentacaoDTO fromEntity(Movimentacao m) {
        if (m == null) return null;
        MovimentacaoDTO dto = new MovimentacaoDTO();
        dto.setId(m.getId());
        dto.setValor(m.getValor());
        dto.setTipo(m.getTipo());
        dto.setData(m.getData());
        if (m.getContaCorrente() != null) dto.setContaCorrenteId(m.getContaCorrente().getId());
        return dto;
    }
}
