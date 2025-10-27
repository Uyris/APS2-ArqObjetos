package com.example.Banco.Movimentacao;

import com.example.Banco.ContaCorrente.ContaCorrente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "movimentacoes")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Float valor;

    @Column(nullable = false)
    private String tipo; // SAQUE ou DEPÓSITO

    @Column(nullable = false)
    private LocalDate data = LocalDate.now();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_conta", nullable = false)
    private ContaCorrente contaCorrente;

    public Movimentacao() {}

    public Movimentacao(float valor, String tipo) {
        this.valor = valor;
        this.tipo = tipo;
        this.data = LocalDate.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Float getValor() { return valor; }
    public void setValor(Float valor) { this.valor = valor; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public ContaCorrente getContaCorrente() { return contaCorrente; }
    public void setContaCorrente(ContaCorrente contaCorrente) { this.contaCorrente = contaCorrente; }
}
