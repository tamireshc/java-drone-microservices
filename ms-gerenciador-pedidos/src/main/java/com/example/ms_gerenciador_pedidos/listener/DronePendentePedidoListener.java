package com.example.ms_gerenciador_pedidos.listener;

import com.example.ms_gerenciador_pedidos.client.MSCadastroResourceClient;
import com.example.ms_gerenciador_pedidos.dto.DroneDTO;
import com.example.ms_gerenciador_pedidos.model.Pedido;
import com.example.ms_gerenciador_pedidos.repository.PedidoRepository;
import com.netflix.discovery.converters.Auto;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DronePendentePedidoListener {

    @Autowired
    private MSCadastroResourceClient msCadastroResourceClient;
    @Auto
    private PedidoRepository pedidoRepository;
    private static final String queueDronePendente = "${rabbitmq.dronependente.queue}";


    @RabbitListener(queues = queueDronePendente)
    @Transactional
    public void completarPedidoComDroneDisponivel(String idPedido) {
        List<DroneDTO> dronesDisponiveis = msCadastroResourceClient.listarDronesPorStatus("DISPONIVEL").getBody();
        Pedido pedido = pedidoRepository.findById(Long.valueOf(idPedido)).orElse(null);

        if (dronesDisponiveis.size() > 0) {
            DroneDTO drone = dronesDisponiveis.get(0);
            pedido.setDroneId(drone.getId());
            pedidoRepository.save(pedido);
        }

        //se nao tiver drone disponivel
        // se a ms-cadastro estiver indiponivel
    }
}
