package com.example.ms_gerenciador_pedidos.model.service;

import com.example.ms_gerenciador_pedidos.dto.PedidoResponseDTO;
import com.example.ms_gerenciador_pedidos.model.Pedido;
import com.example.ms_gerenciador_pedidos.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public PedidoResponseDTO cadastrarPedido(Pedido pedido) {


        Pedido pedidoResponse = pedidoRepository.save(pedido);

        PedidoResponseDTO pedidoResponseDTO = new PedidoResponseDTO();
        pedidoResponseDTO.setId(pedidoResponse.getId());
        pedidoResponseDTO.setDataPedido(pedidoResponse.getDataPedido());
        pedidoResponseDTO.setStatus(pedidoResponse.getStatus().toString());

        return pedidoResponseDTO;

    }

//    private Long id;
//    private LocalDateTime dataPedido;
//    private LocalDateTime dataEntrega;
//    private String status;
//    private EnderecoResponseDTO endereco;
//    private UsuarioResponseDTO usuario;
//    private UsuarioResponseDTO destinatario;
//    private Long droneId;

}
