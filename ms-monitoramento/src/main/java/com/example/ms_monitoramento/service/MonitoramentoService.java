package com.example.ms_monitoramento.service;

import com.example.ms_monitoramento.LatitudeLongitudeDTO;
import com.example.ms_monitoramento.exceptions.MonitoramentoNaoExistenteException;
import com.example.ms_monitoramento.model.Monitoramento;
import com.example.ms_monitoramento.repository.MonitoramentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MonitoramentoService {
    @Autowired
    private MonitoramentoRepository monitoramentoRepository;

    public List<Monitoramento> buscarMonitoramentoPorPedidoId(String pedidoId) {
        List<Monitoramento> monitoramentoPedido = monitoramentoRepository.findByPedidoId(Long.parseLong(pedidoId));
        if (monitoramentoPedido == null) {
            throw new MonitoramentoNaoExistenteException("Monitoramento não encontrado");
        }
        return monitoramentoPedido;
    }

    public Monitoramento buscarPontoDeMonitoramentoPorId(String id) {
        Monitoramento monitoramento = monitoramentoRepository.findById(Long.parseLong(id)).orElse(null);
        if (monitoramento == null) {
            throw new MonitoramentoNaoExistenteException("Monitoramento não encontrado");
        }
        return monitoramento;
    }

    @Transactional
    public Monitoramento editarPontoDeMonitoramento(String id, LatitudeLongitudeDTO latitudeLongitudeDTO) {
        Monitoramento monitoramentoExistente = buscarPontoDeMonitoramentoPorId(id);

        if (latitudeLongitudeDTO.getLatitude() != null) {
            monitoramentoExistente.setLatitude(latitudeLongitudeDTO.getLatitude());
        }
        if (latitudeLongitudeDTO.getLongitude() != null) {
            monitoramentoExistente.setLongitude(latitudeLongitudeDTO.getLongitude());
        }
        return monitoramentoRepository.save(monitoramentoExistente);
    }

    public void deletarPontoDeMonitoramento(String id) {
        Monitoramento monitoramentoExistente = buscarPontoDeMonitoramentoPorId(id);
        monitoramentoRepository.deleteById(Long.parseLong(id));
    }
}
