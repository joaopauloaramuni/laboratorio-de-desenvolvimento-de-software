package com.example.MP_Checkout_Pro.controller;

import com.example.MP_Checkout_Pro.service.MpCheckoutProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class MpCheckoutProController {

    @Autowired
    private MpCheckoutProService mpCheckoutProService;

    @GetMapping("/iniciar-pagamento")
    public String iniciarPagamento(
            @RequestParam String titulo,
            @RequestParam int quantidade,
            @RequestParam BigDecimal precoUnitario) {
        try {
            return mpCheckoutProService.criarPreferencia(titulo, quantidade, precoUnitario);
        } catch (Exception e) {
            // Trate exceções conforme necessário
            return "Erro ao criar preferência de pagamento.";
        }
    }
}


