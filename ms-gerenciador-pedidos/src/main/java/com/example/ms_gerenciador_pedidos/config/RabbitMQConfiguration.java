package com.example.ms_gerenciador_pedidos.config;

import com.example.ms_gerenciador_pedidos.listener.NovoDroneDisponivelListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;


@Configuration
public class RabbitMQConfiguration {
    private ConnectionFactory connectionFactory;

    //Properties
    @Value("${rabbitmq.dronependente.queue}")
    private String queueDronePendente;
    @Value("${rabbitmq.dronependente.queueDLQ}")
    private String queueDronePendenteDLQ;
    @Value("${rabbitmq.dronependente.queueSinal}")
    private String queueDronePendenteSinal;
    @Value("${rabbitmq.dronependente.exchange}")
    private String enchangedDronePendente;
    @Value("${rabbitmq.dronependente.exchangeDLQ}")
    private String enchangedDronePendenteDLQ;
    @Value("${rabbitmq.dronependente.exchangeSinal}")
    private String enchangedDronePendenteSinal;


    //o próprio spring cria um @bean do connectionfactory e injeta
    public RabbitMQConfiguration(ConnectionFactory connectionFactoryr) {
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


//    @Bean
//    public SimpleMessageListenerContainer primaryListenerContainer(ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
//        container.setQueueNames(queueDronePendenteDLQ);
//        container.setAutoStartup(false); // Listener não inicia automaticamente
//        // Configuração do MessageListener deve ser definida posteriormente, conforme necessário
//        return container;
//    }

    @Bean
    public SimpleMessageListenerContainer primaryListenerContainer(ConnectionFactory connectionFactory, NovoDroneDisponivelListener novoDroneDisponivelListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(queueDronePendenteDLQ);
        container.setAutoStartup(false);

        // Set the message listener here:
        container.setMessageListener(novoDroneDisponivelListener::listenDLQDronePendente);

        return container;
    }


    //filas
    @Bean
    public Queue criarFilaDronePendente() {
        return QueueBuilder.durable(queueDronePendente).build();
    }

    @Bean
    public Queue criarFilaDronePendenteDLQ() {
        return QueueBuilder.durable(queueDronePendenteDLQ).build();
    }

    @Bean
    public Queue criarFilaDronePendenteSinal() {
        return QueueBuilder.durable(queueDronePendenteSinal).build();
    }

    //exchanges
    @Bean
    public FanoutExchange criarFanoutExchangeDronePendente() {
        return ExchangeBuilder.fanoutExchange(enchangedDronePendente).build();
    }

    @Bean
    public FanoutExchange criarFanoutExchangeDronePendenteDLQ() {
        return ExchangeBuilder.fanoutExchange(enchangedDronePendenteDLQ).build();
    }

    @Bean
    public FanoutExchange criarFanoutExchangeDronePendenteSinal() {
        return ExchangeBuilder.fanoutExchange(enchangedDronePendenteSinal).build();
    }

    //binding do exchanged Pendente para as filas de endereco pendente
    @Bean
    public Binding criarBindingDronePendeteMsGerenciadorPedidos() {
        return BindingBuilder.bind(criarFilaDronePendente())
                .to(criarFanoutExchangeDronePendente());
    }

    @Bean
    public Binding criarBindingDronePendeteMsGerenciadorPedidosDLQ() {
        return BindingBuilder.bind(criarFilaDronePendenteDLQ())
                .to(criarFanoutExchangeDronePendenteDLQ());
    }

    @Bean
    public Binding criarBindingDronePendeteMsGerenciadorPedidosSinal() {
        return BindingBuilder.bind(criarFilaDronePendenteSinal())
                .to(criarFanoutExchangeDronePendenteSinal());
    }
}
