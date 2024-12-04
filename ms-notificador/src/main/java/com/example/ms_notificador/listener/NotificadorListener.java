package com.example.ms_notificador.listener;

import com.example.ms_notificador.dto.DadosPedidoDTO;
import com.example.ms_notificador.service.NotificacaoSNSService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

import static com.example.ms_notificador.constants.MensagemConstants.*;

@Component
public class NotificadorListener {
    private static final String queueNotificador = "${rabbitmq.notificador.queue}";
    @Autowired
    private NotificacaoSNSService notificacaoSNSService;

    @RabbitListener(queues = queueNotificador)
    @Transactional
    public void EnviarNotificacao(DadosPedidoDTO dadosPedidoDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
        if (dadosPedidoDTO.getStatus().equals("CRIADO")) {
            String mensagem = String.format(
                    PEDIDO_CRIADO,
                    dadosPedidoDTO.getNome(),
                    dadosPedidoDTO.getPedidoId(),
                    dadosPedidoDTO.getDataPedido().format(formatter));
            // Envia notificação de pedido criado
            notificacaoSNSService.notificar(dadosPedidoDTO.getTelefone(), mensagem);
        }
        if (dadosPedidoDTO.getStatus().equals("EM_ROTA")) {
            String mensagem = String.format(
                    PEDIDO_EM_ROTA,
                    dadosPedidoDTO.getNome(),
                    dadosPedidoDTO.getPedidoId(),
                    dadosPedidoDTO.getDataPedido().format(formatter));
            // Envia notificação de pedido em_rota
            notificacaoSNSService.notificar(dadosPedidoDTO.getTelefone(), mensagem);
        }
        if (dadosPedidoDTO.getStatus().equals("ENTREGUE")) {
            String mensagem = String.format(
                    PEDIDO_ENTREGUE,
                    dadosPedidoDTO.getNome(),
                    dadosPedidoDTO.getPedidoId(),
                    dadosPedidoDTO.getDataPedido().format(formatter),
                    dadosPedidoDTO.getDataEntrega().format(formatter));
            // Envia notificação de pedido entregue
            notificacaoSNSService.notificar(dadosPedidoDTO.getTelefone(), mensagem);
        }
        if (dadosPedidoDTO.getStatus().equals("CANCELADO")) {
            String mensagem = String.format(
                    PEDIDO_CANCELADO,
                    dadosPedidoDTO.getNome(),
                    dadosPedidoDTO.getPedidoId(),
                    dadosPedidoDTO.getDataPedido().format(formatter));
            // Envia notificação de pedido cancelado
            notificacaoSNSService.notificar(dadosPedidoDTO.getTelefone(), mensagem);
        }
    }
}
