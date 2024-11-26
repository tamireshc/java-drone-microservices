package com.example.ms_monitoramento.repository;

import com.example.ms_monitoramento.model.DronePedidoId;
import com.example.ms_monitoramento.model.Monitoramento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoramentoRepository extends JpaRepository<Monitoramento, DronePedidoId> {
}
