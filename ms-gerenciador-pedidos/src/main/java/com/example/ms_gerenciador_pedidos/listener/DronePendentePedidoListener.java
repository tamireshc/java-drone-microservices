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


    @RabbitListener(queues = queueDronePendente)
    @Transactional
    public void completarPedidoComDroneDisponivel(String idPedido) {
        List<DroneDTO> dronesDisponiveis = msCadastroResourceClient.listarDronesPorStatus("DISPONIVEL").getBody();
        Pedido pedido = pedidoRepository.findById(Long.valueOf(idPedido)).orElse(null);
        System.out.println(dronesDisponiveis);
        System.out.println(dronesDisponiveis.size());
        System.out.println(pedido);

        if (dronesDisponiveis != null && dronesDisponiveis.size() > 0 && pedido != null) {
            DroneDTO drone = dronesDisponiveis.get(0);
            pedido.setDroneId(drone.getId());
            msCadastroResourceClient.alterarStatusDrone(String.valueOf(drone.getId()), "EM_ROTA");
            pedidoRepository.save(pedido);
        }
        else{
            enviarParaFilaService.enviarBuscaDeDroneDisponivelParaFila(Long.valueOf(idPedido), exchangeDronePendenteDLQ);
        }

        //se nao tiver drone disponivel
        // se a ms-cadastro estiver indiponivel
    }
}
