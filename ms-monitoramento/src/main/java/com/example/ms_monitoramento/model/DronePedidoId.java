package com.example.ms_monitoramento.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class DronePedidoId implements Serializable {
    private Long droneId;
    private Long pedidoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DronePedidoId that = (DronePedidoId) o;
        return droneId.equals(that.droneId) && pedidoId.equals(that.pedidoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(droneId, pedidoId);
    }
}
