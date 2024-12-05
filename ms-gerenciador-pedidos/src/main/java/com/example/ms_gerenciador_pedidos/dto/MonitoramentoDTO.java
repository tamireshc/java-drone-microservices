package com.example.ms_gerenciador_pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonitoramentoDTO {
    private Long pedidoId;
    private Long droneId;
    private String latitude;
    private String longitude;
}
