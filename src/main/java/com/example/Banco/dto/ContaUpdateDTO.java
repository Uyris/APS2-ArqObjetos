package com.example.Banco.dto;

import jakarta.validation.constraints.DecimalMin;

public class ContaUpdateDTO {
    private String agencia;
    // não permitimos alterar numero da conta (chave) via update aqui, se quiser remova a restrição
    @DecimalMin(value = "0.0", inclusive = true, message = "Saldo deve ser >= 0")
    private Float saldo;

    @DecimalMin(value = "0.0", inclusive = true, message = "Limite deve ser >= 0")
    private Float limite;

    public ContaUpdateDTO() {}

    // getters / setters
    public String getAgencia() { return agencia; }
    public void setAgencia(String agencia) { this.agencia = agencia; }

    public Float getSaldo() { return saldo; }
    public void setSaldo(Float saldo) { this.saldo = saldo; }

    public Float getLimite() { return limite; }
    public void setLimite(Float limite) { this.limite = limite; }
}
