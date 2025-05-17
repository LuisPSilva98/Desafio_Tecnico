package com.desafio.api_consulta_credito.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.api_consulta_credito.dto.CreditoDTO;
import com.desafio.api_consulta_credito.dto.LogRequisicaoDTO;
import com.desafio.api_consulta_credito.mapper.CreditoDtoMapper;
import com.desafio.api_consulta_credito.model.Credito;
import com.desafio.api_consulta_credito.producer.KafkaProducer;
import com.desafio.api_consulta_credito.repository.CreditoRepository;
import com.desafio.api_consulta_credito.service.CreditoService;

@Service
public class CreditoServiceImpl implements CreditoService{

    @Autowired
    private CreditoRepository creditoRepository;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    public List<CreditoDTO> buscarCreditosPorNumeroNfse(String numeroNfse) {
        List<Credito> creditos = creditoRepository.findByNumeroNfse(numeroNfse);

        List<CreditoDTO> creditosDTOs = creditos.stream()
        .map(CreditoDtoMapper::converterParaDTO)
        .collect(Collectors.toList());

        LogRequisicaoDTO log = new LogRequisicaoDTO(
            "buscarCreditosPorNumeroNfse",
            numeroNfse,
            creditosDTOs.size(),
            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );

        
        kafkaProducer.send("logs.creditos" + log.toString());

        return creditosDTOs;
    }

    @Override
    public List<CreditoDTO> buscarCreditoPorNumero(String numeroCredito) {
        List<Credito> creditos = creditoRepository.findByNumeroCredito(numeroCredito);

        List<CreditoDTO> creditosDTOs = creditos.stream()
        .map(CreditoDtoMapper::converterParaDTO)
        .collect(Collectors.toList());

            LogRequisicaoDTO log = new LogRequisicaoDTO(
            "buscarCreditoPorNumero",
            numeroCredito,
            creditosDTOs.size(),
            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );

        
        kafkaProducer.send("logs.creditos" + log.toString());

        return creditosDTOs;
    }   
}
