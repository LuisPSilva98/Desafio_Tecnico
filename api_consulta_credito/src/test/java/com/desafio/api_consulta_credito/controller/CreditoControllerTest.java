package com.desafio.api_consulta_credito.controller;

import com.desafio.api_consulta_credito.dto.CreditoDTO;
import com.desafio.api_consulta_credito.service.CreditoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CreditoControllerTest {

    private CreditoService creditoService;
    private CreditoController creditoController;

    @BeforeEach
    void setup() {
        creditoService = Mockito.mock(CreditoService.class);
        creditoController = new CreditoController(creditoService);
    }

    private CreditoDTO criarCreditoDTOExemplo() {
        return CreditoDTO.builder()
                .numeroCredito("CRED001")
                .numeroNfse("NFSE123")
                .dataConstituicao(LocalDate.now())
                .valorIssqn(new BigDecimal("100.00"))
                .tipoCredito("Tipo A")
                .simplesNacional("Sim")
                .aliquota(new BigDecimal("5.00"))
                .valorFaturado(new BigDecimal("500.00"))
                .valorDeducao(new BigDecimal("50.00"))
                .baseCalculo(new BigDecimal("450.00"))
                .build();
    }

    @Test
    @DisplayName("Deve retornar lista de CreditoDTO e status 200 para getCreditoNfse")
    void deveRetornarListaCreditoDTO_quandoBuscarPorNfse() {
        List<CreditoDTO> creditos = List.of(criarCreditoDTOExemplo());

        when(creditoService.buscarCreditosPorNumeroNfse(anyString())).thenReturn(creditos);

        ResponseEntity<List<CreditoDTO>> response = creditoController.getCreditoNfse("NFSE123");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(creditos);
        verify(creditoService, times(1)).buscarCreditosPorNumeroNfse("NFSE123");
    }

    @Test
    @DisplayName("Deve retornar status 204 quando não encontrar créditos para getCreditoNfse")
    void deveRetornarNoContent_quandoListaVaziaParaNfse() {
        when(creditoService.buscarCreditosPorNumeroNfse(anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<CreditoDTO>> response = creditoController.getCreditoNfse("NFSE123");

        assertThat(response.getStatusCode().value()).isEqualTo(204);
        assertThat(response.getBody()).isNull();
        verify(creditoService, times(1)).buscarCreditosPorNumeroNfse("NFSE123");
    }

    @Test
    @DisplayName("Deve retornar lista de CreditoDTO e status 200 para getCreditoNumeroConta")
    void deveRetornarListaCreditoDTO_quandoBuscarPorNumeroCredito() {
        List<CreditoDTO> creditos = List.of(criarCreditoDTOExemplo());

        when(creditoService.buscarCreditoPorNumero(anyString())).thenReturn(creditos);

        ResponseEntity<List<CreditoDTO>> response = creditoController.getCreditoNumeroConta("CRED001");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(creditos);
        verify(creditoService, times(1)).buscarCreditoPorNumero("CRED001");
    }

    @Test
    @DisplayName("Deve retornar status 204 quando não encontrar créditos para getCreditoNumeroConta")
    void deveRetornarNoContent_quandoListaVaziaParaNumeroCredito() {
        when(creditoService.buscarCreditoPorNumero(anyString())).thenReturn(Collections.emptyList());

        ResponseEntity<List<CreditoDTO>> response = creditoController.getCreditoNumeroConta("CRED001");

        assertThat(response.getStatusCode().value()).isEqualTo(204);
        assertThat(response.getBody()).isNull();
        verify(creditoService, times(1)).buscarCreditoPorNumero("CRED001");
    }
}
