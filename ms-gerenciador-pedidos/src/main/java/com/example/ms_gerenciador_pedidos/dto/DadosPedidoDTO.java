package com.example.ms_gerenciador_pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DadosPedidoDTO {
    private Long pedidoId;
    private String Status;
    private String nome;
    private String telefone;
    private LocalDateTime dataPedido;
    private LocalDateTime dataEntrega;
}
