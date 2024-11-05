package com.example.ms_gerenciador_pedidos.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    private ConnectionFactory connectionFactory;

    //Properties
    @Value("${rabbitmq.dronependente.queue}")
    private String queueDronePendente;
    @Value("${rabbitmq.dronependente.exchange}")
    private String enchangedDronePendente;

    //o próprio spring cria um @bean do connectionfactory e injeta
    public RabbitMQConfiguration(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    // inicializa as filas descritas no @bean no rabbitMQ
    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializerAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    //cria um template para enviar mensagens que utiliza o jackson2JsonMessageConverter
    //Converte automaticamente objetos Java em JSON quando envia mensagens
    //Converte JSON em objetos Java quando recebe mensagens.

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    //filas
    @Bean
    public Queue criarFilaDronePendente() {
        return QueueBuilder.durable(queueDronePendente).build();
    }

    //exchanges
    @Bean
    public FanoutExchange criarFanoutExchangeDronePendente() {
        return ExchangeBuilder.fanoutExchange(enchangedDronePendente).build();
    }

    //binding do exchanged Pendente para as filas de endereco pendente
    @Bean
    public Binding criarBindingDronePendeteMsGerenciadorPedidos() {
        return BindingBuilder.bind(criarFilaDronePendente())
                .to(criarFanoutExchangeDronePendente());
    }
}
