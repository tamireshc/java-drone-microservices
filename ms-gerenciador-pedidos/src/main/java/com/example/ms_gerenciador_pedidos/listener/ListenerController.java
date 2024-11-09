package com.example.ms_gerenciador_pedidos.listener;

import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListenerController {

    @Autowired
    private MessageListenerContainer primaryListenerContainer;

    public void startListener() {
        primaryListenerContainer.start();
        System.out.println("Listener iniciado com sucesso.");
    }

    public void stopListener() throws InterruptedException {
        primaryListenerContainer.stop();
        System.out.println("Listener parado com sucesso.");
    }
}

