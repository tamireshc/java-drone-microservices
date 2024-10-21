package com.example.ms_gerenciador_.cadastros.service;

import com.example.ms_gerenciador_.cadastros.model.Endereco;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LatitudeLongitudeService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendEndereco(Endereco endereco, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", endereco);
    }
}
