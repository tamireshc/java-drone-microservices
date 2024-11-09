package com.example.ms_gerenciador_pedidos.listener;

import com.example.ms_gerenciador_pedidos.client.MSCadastroResourceClient;
import com.example.ms_gerenciador_pedidos.dto.DroneDTO;
import com.example.ms_gerenciador_pedidos.model.Pedido;
import com.example.ms_gerenciador_pedidos.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import java.util.List;
import static java.lang.Thread.sleep;

@Component
public class NovoDroneDisponivelListener {

    @Autowired
    private MSCadastroResourceClient msCadastroResourceClient;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    @Lazy
    private ListenerController listenerController;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    // Listener da fila de sinalização
    @RabbitListener(queues = "${rabbitmq.dronedisponivel.queueSinal}")
    public void controlarListener(String message) throws InterruptedException {
        if ("START".equalsIgnoreCase(message)) {
            listenerController.startListener();
            sleep(1000);
            listenerController.stopListener();
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