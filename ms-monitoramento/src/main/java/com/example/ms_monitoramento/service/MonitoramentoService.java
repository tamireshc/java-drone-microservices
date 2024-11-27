package com.example.ms_monitoramento.service;

import com.example.ms_monitoramento.LatitudeLongitudeDTO;
import com.example.ms_monitoramento.exceptions.MonitoramentoNaoExistenteException;
import com.example.ms_monitoramento.model.DronePedidoId;
import com.example.ms_monitoramento.model.Monitoramento;
import com.example.ms_monitoramento.repository.MonitoramentoRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Monitoramento editarMonitoramento(String droneId, String pedidoId, LatitudeLongitudeDTO latitudeLongitudeDTO) {
        Monitoramento monitoramentoExistente = buscarMonitoramentoPorId(droneId, pedidoId);
        if (latitudeLongitudeDTO.getLatitude() != null) {
            monitoramentoExistente.setLatitude(latitudeLongitudeDTO.getLatitude());
        }
        if (latitudeLongitudeDTO.getLongitude() != null) {
            monitoramentoExistente.setLongitude(latitudeLongitudeDTO.getLongitude());
        }
        return monitoramentoRepository.save(monitoramentoExistente);
    }
}
