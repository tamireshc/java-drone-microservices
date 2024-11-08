package com.example.ms_gerenciador_pedidos.listener;

import com.example.ms_gerenciador_pedidos.client.MSCadastroResourceClient;
import com.example.ms_gerenciador_pedidos.dto.DroneDTO;
import com.example.ms_gerenciador_pedidos.model.Pedido;
import com.example.ms_gerenciador_pedidos.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;

import java.util.List;

@Component
public class NovoDroneDisponivelListener {

    @Autowired
    private MSCadastroResourceClient msCadastroResourceClient;
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    @Lazy
    private ListenerController listenerController;


    // Listener da fila de sinalização
    @RabbitListener(queues = "${rabbitmq.dronependente.queueSinal}")
    public void controlarListener(String message) {
        if ("START".equalsIgnoreCase(message)) {
            listenerController.startListener();
        } else if ("STOP".equalsIgnoreCase(message)) {
            listenerController.stopListener();
        }
    }

    @Transactional
    public void listenDLQDronePendente(Message message) {
        List<DroneDTO> dronesDisponiveis = msCadastroResourceClient.listarDronesPorStatus("DISPONIVEL").getBody();
        String idPedido = new String(message.getBody());
        Pedido pedido = pedidoRepository.findById(Long.valueOf(idPedido)).orElse(null);
        DroneDTO drone = dronesDisponiveis.get(0);
        pedido.setDroneId(drone.getId());
        msCadastroResourceClient.alterarStatusDrone(String.valueOf(drone.getId()), "EM_ROTA");
        pedidoRepository.save(pedido);
    }
}