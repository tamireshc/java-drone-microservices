package com.example.ms_gerenciador_pedidos.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PedidoResponseDTO {
    private Long id;
    private LocalDateTime dataPedido;
    private LocalDateTime dataEntrega;
    private String status;
    private EnderecoResponseDTO endereco;
    private UsuarioResponseDTO usuario;
    private UsuarioResponseDTO destinatario;
    private Long droneId;
}
