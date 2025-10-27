package com.example.Banco.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    void deleteByCpf(String cpf);
}
