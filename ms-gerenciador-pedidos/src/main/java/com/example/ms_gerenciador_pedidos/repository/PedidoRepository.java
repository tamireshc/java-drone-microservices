package com.example.ms_gerenciador_pedidos.repository;

import com.example.ms_gerenciador_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
