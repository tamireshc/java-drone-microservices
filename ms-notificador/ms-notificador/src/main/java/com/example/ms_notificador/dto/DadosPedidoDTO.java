package com.example.ms_notificador.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DadosPedidoDTO {
    private Long pedidoId;
    private String Status;
    private String nome;
    private String telefone;
    private LocalDateTime dataPedido;
    private LocalDateTime dataEntrega;
}
