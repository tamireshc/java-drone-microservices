package com.example.ms_gerenciador_pedidos.model;

import com.example.ms_gerenciador_pedidos.model.enuns.StatusPedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime dataPedido;
    private LocalDateTime dataEntrega;
    @Column(nullable = false)
    private StatusPedido status;
    @Column(nullable = false)
    private Long enderecoId;
    @Column(nullable = false)
    private Long usuarioId;
    @Column(nullable = false)
    private Long destinatarioId;
    private Long droneId;
}
