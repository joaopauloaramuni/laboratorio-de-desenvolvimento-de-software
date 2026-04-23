package com.example.RabbitMQ.service;

import com.example.RabbitMQ.config.RabbitConfig;
import com.example.RabbitMQ.dto.PedidoIngressoDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // Producer
    public void comprarIngresso(PedidoIngressoDTO pedido) {
        rabbitTemplate.convertAndSend(RabbitConfig.FILA_VENDA, pedido);
        System.out.println("🟡 Pedido enviado: " + pedido.getNomeCliente());
    }

    // Consumer
    @RabbitListener(queues = RabbitConfig.FILA_VENDA)
    public void processarCompra(PedidoIngressoDTO pedido) {
        System.out.println("🟢 Processando pedido:");
        System.out.println("Cliente: " + pedido.getNomeCliente());
        System.out.println("Evento: " + pedido.getEvento());
        System.out.println("Quantidade: " + pedido.getQuantidade());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Ingresso emitido!");
    }
}
