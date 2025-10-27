package com.example.Banco.ContaCorrente;

import com.example.Banco.Cartao.Cartao;
import com.example.Banco.Cliente.Cliente;
import com.example.Banco.Movimentacao.Movimentacao;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contas_corrente", indexes = {@Index(name = "idx_numero_conta", columnList = "numero", unique = true)})
public class ContaCorrente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String agencia;

    @Column(nullable = false, unique = true)
    private String numero;

    @Column(nullable = false)
    private Float saldo = 0f;

    @Column(nullable = false)
    private Float limite = 0f;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cliente", unique = true)
    private Cliente cliente;

    @OneToMany(mappedBy = "contaCorrente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movimentacao> movimentacoes = new ArrayList<>();

    @OneToMany(mappedBy = "contaCorrente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cartao> cartoes = new ArrayList<>();

    public ContaCorrente() {}

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

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        if (cliente != null && cliente.getContaCorrente() != this) {
            cliente.setContaCorrente(this);
        }
    }

    public List<Cartao> getCartoes() { return cartoes; }
    public void setCartoes(List<Cartao> cartoes) { this.cartoes = cartoes; }

    public List<Movimentacao> getMovimentacoes() { return movimentacoes; }
    public void setMovimentacoes(List<Movimentacao> movimentacoes) { this.movimentacoes = movimentacoes; }

    public void adicionarCartao(Cartao cartao) {
        cartoes.add(cartao);
        cartao.setContaCorrente(this);
        cartao.setNumeroDaConta(this.numero);
    }

    public void removerCartao(Cartao cartao) {
        cartoes.remove(cartao);
        cartao.setContaCorrente(null);
    }

    public void adicionarMovimentacao(Movimentacao mv) {
        movimentacoes.add(mv);
        mv.setContaCorrente(this);
    }
}
