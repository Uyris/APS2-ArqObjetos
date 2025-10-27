package com.example.Banco.dto;

import com.example.Banco.Cartao.Cartao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public class CartaoCreateDTO {

    // numeroCartao: obrigatório (você pode adicionar regex se quiser)
    @NotBlank(message = "numeroCartao é obrigatório")
    private String numeroCartao;

    // tipo: obrigatório, exemplo: "DEBITO" ou "CREDITO"
    @NotBlank(message = "tipo é obrigatório")
    private String tipo;

    // validade: opcional no request — se nulo, entidade define default
    private LocalDate validade;

    // numeroDaConta: obrigatório para emitir
    @NotBlank(message = "numeroDaConta é obrigatório")
    private String numeroDaConta;

    public CartaoCreateDTO() {}

    // getters / setters
    public String getNumeroCartao() { return numeroCartao; }
    public void setNumeroCartao(String numeroCartao) { this.numeroCartao = numeroCartao; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDate getValidade() { return validade; }
    public void setValidade(LocalDate validade) { this.validade = validade; }

    public String getNumeroDaConta() { return numeroDaConta; }
    public void setNumeroDaConta(String numeroDaConta) { this.numeroDaConta = numeroDaConta; }

    // Mapper: DTO -> entity (não associa ContaCorrente aqui; o service faz essa associação)
    public Cartao toEntity() {
        Cartao c = new Cartao();
        c.setNumeroCartao(this.numeroCartao);
        c.setTipo(this.tipo);
        if (this.validade != null) c.setValidade(this.validade);
        c.setNumeroDaConta(this.numeroDaConta);
        return c;
    }
}
