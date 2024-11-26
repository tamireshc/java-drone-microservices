package com.example.ms_monitoramento.service;

import com.example.ms_monitoramento.exceptions.MonitoramentoNaoExistenteException;
import com.example.ms_monitoramento.model.DronePedidoId;
import com.example.ms_monitoramento.model.Monitoramento;
import com.example.ms_monitoramento.repository.MonitoramentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitoramentoService {
    @Autowired
    private MonitoramentoRepository monitoramentoRepository;

    public Monitoramento buscarMonitoramentoPorId(String droneId, String pedidoId) {
        DronePedidoId id = new DronePedidoId(Long.parseLong(droneId), Long.parseLong(pedidoId));
        Monitoramento monitoramento = monitoramentoRepository.findById(id).orElse(null);
        if (monitoramento == null) {
            throw new MonitoramentoNaoExistenteException("Monitoramento não encontrado");
        }
        return monitoramento;
    }
}
