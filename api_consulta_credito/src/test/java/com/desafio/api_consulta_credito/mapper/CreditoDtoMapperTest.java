package com.desafio.api_consulta_credito.mapper;

import com.desafio.api_consulta_credito.dto.CreditoDTO;
import com.desafio.api_consulta_credito.model.Credito;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CreditoDtoMapperTest {

    @Test
    @DisplayName("Deve converter entidade Credito para CreditoDTO corretamente")
    void deveConverterParaDTO() {
        // Arrange
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

        // Act
        CreditoDTO dto = CreditoDtoMapper.converterParaDTO(credito);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getNumeroCredito()).isEqualTo("CRED123");
        assertThat(dto.getNumeroNfse()).isEqualTo("NFSE456");
        assertThat(dto.getDataConstituicao()).isEqualTo(LocalDate.of(2023, 5, 17));
        assertThat(dto.getValorIssqn()).isEqualByComparingTo("150.00");
        assertThat(dto.getTipoCredito()).isEqualTo("Tipo Teste");
        assertThat(dto.getSimplesNacional()).isEqualTo("Sim");
        assertThat(dto.getAliquota()).isEqualByComparingTo("7.5");
        assertThat(dto.getValorFaturado()).isEqualByComparingTo("1000.00");
        assertThat(dto.getValorDeducao()).isEqualByComparingTo("100.00");
        assertThat(dto.getBaseCalculo()).isEqualByComparingTo("900.00");
    }

    @Test
    @DisplayName("Deve retornar null quando entidade Credito for null")
    void deveRetornarNullQuandoCreditoForNull() {
        // Act
        CreditoDTO dto = CreditoDtoMapper.converterParaDTO(null);

        // Assert
        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("Deve setar SimplesNacional como 'Não' quando false")
    void deveConverterSimplesNacionalFalse() {
        // Arrange
        Credito credito = new Credito();
        credito.setSimplesNacional(false);

        // Act
        CreditoDTO dto = CreditoDtoMapper.converterParaDTO(credito);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getSimplesNacional()).isEqualTo("Não");
    }
}
