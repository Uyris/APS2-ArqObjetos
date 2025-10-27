package com.example.Banco.dto;

import com.example.Banco.ContaCorrente.ContaCorrente;
import java.util.List;
import java.util.stream.Collectors;

public class ContaCorrenteDTO {
    private Integer id;
    private String agencia;
    private String numero;
    private Float saldo;
    private Float limite;
    private Integer clienteId;
    private List<Integer> cartaoIds;
    private List<Integer> movimentacaoIds;

    public ContaCorrenteDTO() {}

    // getters / setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getAgencia() { return agencia; }
    public void setAgencia(String agencia) { this.agencia = agencia; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public Float getSaldo() { return saldo; }
    public void setSaldo(Float saldo) { this.saldo = saldo; }

    public Float getLimite() { return limite; }
    public void setLimite(Float limite) { this.limite = limite; }

    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }

    public List<Integer> getCartaoIds() { return cartaoIds; }
    public void setCartaoIds(List<Integer> cartaoIds) { this.cartaoIds = cartaoIds; }

    public List<Integer> getMovimentacaoIds() { return movimentacaoIds; }
    public void setMovimentacaoIds(List<Integer> movimentacaoIds) { this.movimentacaoIds = movimentacaoIds; }

    // mapper entity -> DTO
    public static ContaCorrenteDTO fromEntity(ContaCorrente c) {
        if (c == null) return null;
        ContaCorrenteDTO dto = new ContaCorrenteDTO();
        dto.setId(c.getId());
        dto.setAgencia(c.getAgencia());
        dto.setNumero(c.getNumero());
        dto.setSaldo(c.getSaldo());
        dto.setLimite(c.getLimite());
        if (c.getCliente() != null) dto.setClienteId(c.getCliente().getId());
        if (c.getCartoes() != null) dto.setCartaoIds(
                c.getCartoes().stream().map(cart -> cart.getId()).collect(Collectors.toList())
        );
        if (c.getMovimentacoes() != null) dto.setMovimentacaoIds(
                c.getMovimentacoes().stream().map(mv -> mv.getId()).collect(Collectors.toList())
        );
        return dto;
    }
}
