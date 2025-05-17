package com.desafio.api_consulta_credito.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogRequisicaoDTO {

    private String metodo;
    private String numeroNfse;
    private int totalResultados;
    private String timestamp;
}
