package com.desafio.api_consulta_credito.service.impl;

import com.desafio.api_consulta_credito.dto.CreditoDTO;
import com.desafio.api_consulta_credito.model.Credito;
import com.desafio.api_consulta_credito.producer.KafkaProducer;
import com.desafio.api_consulta_credito.repository.CreditoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CreditoServiceImplTest {

    @InjectMocks
    private CreditoServiceImpl creditoService;

    @Mock
    private CreditoRepository creditoRepository;

    @Mock
    private KafkaProducer kafkaProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Credito criarCreditoExemplo() {
        Credito credito = new Credito();
        credito.setNumeroCredito("CRED123");
        credito.setNumeroNfse("NFSE456");
        credito.setDataConstituicao(LocalDate.of(2023, 5, 17));
        credito.setValorIssqn(new BigDecimal("150.00"));
        credito.setTipoCredito("Tipo Teste");
        credito.setSimplesNacional(true);
        credito.setAliquota(new BigDecimal("7.5"));
        credito.setValorFaturado(new BigDecimal("1000.00"));
        credito.setValorDeducao(new BigDecimal("100.00"));
        credito.setBaseCalculo(new BigDecimal("900.00"));
        return credito;
    }

    @Test
    @DisplayName("Deve retornar lista de CreditoDTO ao buscar por número NFSE e enviar log")
    void deveBuscarCreditosPorNumeroNfse() {
        // Arrange
        List<Credito> listaCreditos = new ArrayList<>();
        listaCreditos.add(criarCreditoExemplo());
        when(creditoRepository.findByNumeroNfse("NFSE456")).thenReturn(listaCreditos);

        // Act
        List<CreditoDTO> resultado = creditoService.buscarCreditosPorNumeroNfse("NFSE456");

        // Assert
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getNumeroNfse()).isEqualTo("NFSE456");

        // Verifica se o kafkaProducer.send foi chamado pelo menos uma vez
        verify(kafkaProducer, times(1)).send(anyString());
    }

    @Test
    @DisplayName("Deve retornar lista de CreditoDTO ao buscar por número crédito e enviar log")
    void deveBuscarCreditoPorNumero() {
        // Arrange
        List<Credito> listaCreditos = new ArrayList<>();
        listaCreditos.add(criarCreditoExemplo());
        when(creditoRepository.findByNumeroCredito("CRED123")).thenReturn(listaCreditos);

        // Act
        List<CreditoDTO> resultado = creditoService.buscarCreditoPorNumero("CRED123");

        // Assert
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getNumeroCredito()).isEqualTo("CRED123");

        // Verifica se o kafkaProducer.send foi chamado
        verify(kafkaProducer, times(1)).send(anyString());
    }

    @Test
    @DisplayName("Deve retornar lista vazia e enviar log ao não encontrar créditos por NFSE")
    void deveRetornarListaVaziaPorNumeroNfseInexistente() {
        // Arrange
        when(creditoRepository.findByNumeroNfse("NFSE999")).thenReturn(List.of());

        // Act
        List<CreditoDTO> resultado = creditoService.buscarCreditosPorNumeroNfse("NFSE999");

        // Assert
        assertThat(resultado).isEmpty();
        verify(kafkaProducer, times(1)).send(anyString());
    }

    @Test
    @DisplayName("Deve retornar lista vazia e enviar log ao não encontrar créditos por número crédito")
    void deveRetornarListaVaziaPorNumeroCreditoInexistente() {
        // Arrange
        when(creditoRepository.findByNumeroCredito("CRED999")).thenReturn(List.of());

        // Act
        List<CreditoDTO> resultado = creditoService.buscarCreditoPorNumero("CRED999");

        // Assert
        assertThat(resultado).isEmpty();
        verify(kafkaProducer, times(1)).send(anyString());
    }
}
