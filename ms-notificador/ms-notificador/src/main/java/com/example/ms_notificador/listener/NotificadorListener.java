package com.example.ms_notificador.listener;

import com.example.ms_notificador.dto.DadosPedidoDTO;
import com.example.ms_notificador.service.NotificacaoSNSService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.example.ms_notificador.constants.MensagemConstants.*;

@Component
public class NotificadorListener {
    private static final String queueNovoMonitoramento = "${rabbitmq.notificador.queue}";
    @Autowired
    private NotificacaoSNSService notificacaoSNSService;

    @RabbitListener(queues = queueNovoMonitoramento)
    @Transactional
    public void EnviarNotificacao(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            DadosPedidoDTO dadosPedidoDTO = objectMapper.readValue(message, DadosPedidoDTO.class);
            if (dadosPedidoDTO.getStatus().equals("CRIADO")) {
                String mensagem = String.format(
                        PEDIDO_CRIADO,
                        dadosPedidoDTO.getNome(),
                        dadosPedidoDTO.getPedidoId(),
                        dadosPedidoDTO.getDataPedido());
                // Envia notificação de pedido criado
                notificacaoSNSService.notificar(dadosPedidoDTO.getTelefone(), message);
            }
            if (dadosPedidoDTO.getStatus().equals("EM_ROTA")) {
                String mensagem = String.format(
                        PEDIDO_EM_ROTA,
                        dadosPedidoDTO.getNome(),
                        dadosPedidoDTO.getPedidoId(),
                        dadosPedidoDTO.getDataPedido());
                // Envia notificação de pedido em_rota
                notificacaoSNSService.notificar(dadosPedidoDTO.getTelefone(), message);
            }
            if (dadosPedidoDTO.getStatus().equals("ENTREGUE")) {
                String mensagem = String.format(
                        PEDIDO_ENTREGUE,
                        dadosPedidoDTO.getNome(),
                        dadosPedidoDTO.getPedidoId(),
                        dadosPedidoDTO.getDataPedido(),
                        dadosPedidoDTO.getDataEntrega());
                // Envia notificação de pedido entregue
                notificacaoSNSService.notificar(dadosPedidoDTO.getTelefone(), message);
            }
            if (dadosPedidoDTO.getStatus().equals("CANCELADO")) {
                String mensagem = String.format(
                        PEDIDO_CANCELADO,
                        dadosPedidoDTO.getNome(),
                        dadosPedidoDTO.getPedidoId(),
                        dadosPedidoDTO.getDataPedido());
                // Envia notificação de pedido cancelado
                notificacaoSNSService.notificar(dadosPedidoDTO.getTelefone(), message);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
