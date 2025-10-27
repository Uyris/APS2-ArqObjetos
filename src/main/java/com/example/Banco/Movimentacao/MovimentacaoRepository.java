package com.example.Banco.Movimentacao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer> {
    List<Movimentacao> findByContaCorrente_Numero(String numeroConta);
}
