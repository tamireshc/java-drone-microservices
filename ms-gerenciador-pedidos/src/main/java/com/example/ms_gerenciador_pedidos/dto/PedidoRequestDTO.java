package com.example.ms_gerenciador_pedidos.dto;

import com.example.ms_gerenciador_pedidos.model.Pedido;
import com.example.ms_gerenciador_pedidos.model.enuns.StatusPedido;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PedidoRequestDTO {
    private LocalDateTime dataPedido;
    private String status;
    private Long enderecoId;
    private Long usuarioId;
    private Long destinatarioId;

    public Pedido toPedido(PedidoRequestDTO pedidoRequestDTO) {
        Pedido pedido = new Pedido();
        pedido.setDataPedido(pedidoRequestDTO.getDataPedido());
        pedido.setStatus(StatusPedido.valueOf(pedidoRequestDTO.getStatus().toUpperCase()));
        pedido.setEnderecoId(pedidoRequestDTO.getEnderecoId());
        pedido.setUsuarioId(pedidoRequestDTO.getUsuarioId());
        pedido.setDestinatarioId(pedidoRequestDTO.getDestinatarioId());
        return pedido;
    }
}
