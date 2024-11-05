package com.example.ms_gerenciador_pedidos.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnviarParaFilaService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarBuscaDeDroneDisponivelParaFila(Long id, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", id);
    }
}
