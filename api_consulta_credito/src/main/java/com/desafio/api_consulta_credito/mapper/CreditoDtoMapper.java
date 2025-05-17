package com.desafio.api_consulta_credito.mapper;

import com.desafio.api_consulta_credito.dto.CreditoDTO;
import com.desafio.api_consulta_credito.model.Credito;


public class CreditoDtoMapper {

    public static CreditoDTO converterParaDTO(Credito credito) {
        if (credito == null) {
            return null; 
        }
        return CreditoDTO.builder()
        .numeroCredito(credito.getNumeroCredito())
        .numeroNfse(credito.getNumeroNfse())
        .dataConstituicao(credito.getDataConstituicao())
        .valorIssqn(credito.getValorIssqn())
        .tipoCredito(credito.getTipoCredito())
        .simplesNacional(credito.isSimplesNacional() ? "Sim" : "Não")
        .aliquota(credito.getAliquota())
        .valorFaturado(credito.getValorFaturado())
        .valorDeducao(credito.getValorDeducao())
        .baseCalculo(credito.getBaseCalculo())
        .build();
    }
}
