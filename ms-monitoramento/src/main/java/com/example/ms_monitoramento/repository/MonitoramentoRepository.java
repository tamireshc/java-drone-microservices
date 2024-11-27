package com.example.ms_monitoramento.repository;

import com.example.ms_monitoramento.model.Monitoramento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonitoramentoRepository extends JpaRepository<Monitoramento, Long> {
    List<Monitoramento> findByPedidoId(long pedidoId);
}
