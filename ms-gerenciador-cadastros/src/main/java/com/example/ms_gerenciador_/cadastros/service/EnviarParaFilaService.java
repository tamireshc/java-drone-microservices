package com.example.ms_gerenciador_.cadastros.service;

import com.example.ms_gerenciador_.cadastros.model.Endereco;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnviarParaFilaService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarEnderecoParaFila(Endereco endereco, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", endereco);
    }

    public void enviarDroneDisponivelParaFila(String message, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", message);
    }
}
