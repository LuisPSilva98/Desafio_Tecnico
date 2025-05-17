package com.desafio.api_consulta_credito.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.api_consulta_credito.dto.CreditoDTO;
import com.desafio.api_consulta_credito.service.CreditoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/creditos")
public class CreditoController {

    private CreditoService creditoService;

    public CreditoController(CreditoService creditoService){
        this.creditoService = creditoService;
    }

    @GetMapping("{nfse}")
    public ResponseEntity<List<CreditoDTO>> getCreditoNfse(@PathVariable String nfse) {
            List<CreditoDTO> creditos = creditoService.buscarCreditosPorNumeroNfse(nfse);
        if (creditos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(creditos);
    }

    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<List<CreditoDTO>> getCreditoNumeroConta(@PathVariable String numeroCredito) {
        List<CreditoDTO> creditos = creditoService.buscarCreditoPorNumero(numeroCredito);
        if(creditos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(creditos);
    }
}
