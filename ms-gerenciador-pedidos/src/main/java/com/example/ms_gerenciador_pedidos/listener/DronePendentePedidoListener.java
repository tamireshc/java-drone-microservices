package com.example.ms_gerenciador_pedidos.listener;

import com.example.ms_gerenciador_pedidos.client.MSCadastroResourceClient;
import com.example.ms_gerenciador_pedidos.dto.DroneDTO;
import com.example.ms_gerenciador_pedidos.model.Pedido;
import com.example.ms_gerenciador_pedidos.repository.PedidoRepository;
import com.example.ms_gerenciador_pedidos.service.EnviarParaFilaService;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DronePendentePedidoListener {

    @Autowired
    private MSCadastroResourceClient msCadastroResourceClient;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    EnviarParaFilaService enviarParaFilaService;
    private static final String queueDronePendente = "${rabbitmq.dronependente.queue}";
    @Value("${rabbitmq.dronependente.exchangeDLQ}")
    private String exchangeDronePendenteDLQ;
    @Autowired
    @Lazy
    private ListenerController listenerController;

    @RabbitListener(queues = queueDronePendente)
    @Transactional
    public void completarPedidoComDroneDisponivel(String idPedido) throws InterruptedException {
        List<DroneDTO> dronesDisponiveis = msCadastroResourceClient.listarDronesPorStatus("DISPONIVEL").getBody();
        Pedido pedido = pedidoRepository.findById(Long.valueOf(idPedido)).orElse(null);

        if (dronesDisponiveis != null && dronesDisponiveis.size() > 0 && pedido != null) {
            DroneDTO drone = dronesDisponiveis.get(0);
            pedido.setDroneId(drone.getId());
            msCadastroResourceClient.alterarStatusDrone(String.valueOf(drone.getId()), "EM_ROTA");
            pedidoRepository.save(pedido);
            listenerController.stopListener();
        }
        //Se não hover drone disponível enviar para DLQ de drone pendente
        else {
            enviarParaFilaService.enviarBuscaDeDroneDisponivelParaFila(Long.valueOf(idPedido), exchangeDronePendenteDLQ);
        }
        // se a ms-cadastro estiver indiponivel
    }
}
