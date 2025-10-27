package com.example.Banco.Cartao;

import com.example.Banco.ContaCorrente.ContaCorrente;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "cartoes", indexes = {@Index(name = "idx_numero_cartao", columnList = "numeroCartao", unique = true)})
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String numeroCartao;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private LocalDate validade = LocalDate.now().plusYears(10);

    @Column(nullable = false)
    private String status = "ATIVO";

    @Column(nullable = false)
    private String numeroDaConta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_conta")
    private ContaCorrente contaCorrente;

    public Cartao() {}

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

    public ContaCorrente getContaCorrente() { return contaCorrente; }
    public void setContaCorrente(ContaCorrente contaCorrente) { this.contaCorrente = contaCorrente; }

    public boolean isExpired() { return validade.isBefore(LocalDate.now()); }
    public void cancelaCartao() { this.status = "CANCELADO"; }
}
