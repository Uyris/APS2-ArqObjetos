package com.example.Banco.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ContaCreateDTO {

    @NotBlank(message = "agencia é obrigatória")
    private String agencia;

    @NotBlank(message = "numero é obrigatório")
    private String numero;

    // saldo opcional (default no entity)
    private Float saldo;

    // limite opcional
    private Float limite;

    @NotBlank(message = "cpf do cliente é obrigatório")
    private String clienteCpf;

    public ContaCreateDTO() {}

    // getters / setters
    public String getAgencia() { return agencia; }
    public void setAgencia(String agencia) { this.agencia = agencia; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public Float getSaldo() { return saldo; }
    public void setSaldo(Float saldo) { this.saldo = saldo; }

    public Float getLimite() { return limite; }
    public void setLimite(Float limite) { this.limite = limite; }

    public String getClienteCpf() { return clienteCpf; }
    public void setClienteCpf(String clienteCpf) { this.clienteCpf = clienteCpf; }

    // toEntity parcial — o service vai associar o cliente corretamente
    public com.example.Banco.ContaCorrente.ContaCorrente toEntity() {
        com.example.Banco.ContaCorrente.ContaCorrente c = new com.example.Banco.ContaCorrente.ContaCorrente();
        c.setAgencia(this.agencia);
        c.setNumero(this.numero);
        if (this.saldo != null) c.setSaldo(this.saldo);
        if (this.limite != null) c.setLimite(this.limite);
        // não setCliente aqui; o controller/service irá buscar por cpf e setar
        return c;
    }
}
