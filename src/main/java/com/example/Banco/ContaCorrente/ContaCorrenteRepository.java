package com.example.Banco.ContaCorrente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Integer> {
    Optional<ContaCorrente> findByNumero(String numero);
    boolean existsByNumero(String numero);
    void deleteByNumero(String numero);
}
