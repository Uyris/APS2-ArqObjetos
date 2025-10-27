package com.example.Banco.Cartao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, Integer> {
    Optional<Cartao> findByNumeroCartao(String numeroCartao);
    boolean existsByNumeroCartao(String numeroCartao);
    void deleteByNumeroCartao(String numeroCartao);
}
