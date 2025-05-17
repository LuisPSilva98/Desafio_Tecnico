package com.desafio.api_consulta_credito.service;

import java.util.List;

import com.desafio.api_consulta_credito.dto.CreditoDTO;

public interface CreditoService {
    List<CreditoDTO> buscarCreditosPorNumeroNfse(String numeroNfse);

    List<CreditoDTO> buscarCreditoPorNumero(String numeroCredito);
}
