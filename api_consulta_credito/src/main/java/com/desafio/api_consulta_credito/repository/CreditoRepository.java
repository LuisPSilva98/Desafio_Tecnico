package com.desafio.api_consulta_credito.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.api_consulta_credito.model.Credito;

public interface CreditoRepository extends JpaRepository<Credito, Long>{
    List<Credito> findByNumeroNfse(String numeroNfse);

    List<Credito> findByNumeroCredito(String numeroCredito);
}
