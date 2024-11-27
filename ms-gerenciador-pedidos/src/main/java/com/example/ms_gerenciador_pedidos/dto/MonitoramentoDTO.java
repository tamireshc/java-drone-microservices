package com.example.ms_gerenciador_pedidos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitoramentoDTO {
    private Long pedidoId;
    private Long droneId;
    private String latitude;
    private String longitude;
}
