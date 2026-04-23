package com.example.RabbitMQ.controller;

import com.example.RabbitMQ.dto.PedidoIngressoDTO;
import com.example.RabbitMQ.service.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingressos")
public class RabbitMqController {

    @Autowired
    private RabbitMqService rabbitMqService;

    @PostMapping("/comprar")
    public String comprar(@RequestBody PedidoIngressoDTO pedido) {
        rabbitMqService.comprarIngresso(pedido);
        return "Pedido enviado para processamento!";
    }
}
