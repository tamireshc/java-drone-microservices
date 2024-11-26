package com.example.ms_monitoramento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Monitoramento {
    @EmbeddedId
    private DronePedidoId id;
    private String latitude;
    private String longitude;
}
