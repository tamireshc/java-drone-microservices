package com.example.ms_monitoramento.service;

import com.example.ms_monitoramento.dto.LatitudeLongitudeDTO;
import com.example.ms_monitoramento.exceptions.MonitoramentoNaoExistenteException;
import com.example.ms_monitoramento.exceptions.PedidoNaoEncontradoException;
import com.example.ms_monitoramento.model.Monitoramento;
import com.example.ms_monitoramento.repository.MonitoramentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoramentoService {
    @Autowired
    private MonitoramentoRepository monitoramentoRepository;

    @Transactional
    public Monitoramento cadastrarMonitoramento(Monitoramento monitoramento) {
        List<Monitoramento> monitoramentoPedido = buscarMonitoramentoPorPedidoId(String.valueOf(monitoramento.getPedidoId()));
        return monitoramentoRepository.save(monitoramento);
    }

    @Transactional
    public Monitoramento criarMonitoramento(Monitoramento monitoramento) {
        return monitoramentoRepository.save(monitoramento);
    }

    public List<Monitoramento> buscarMonitoramentoPorPedidoId(String pedidoId) {
        List<Monitoramento> monitoramentoPedido = monitoramentoRepository.findByPedidoId(Long.parseLong(pedidoId));
        if (monitoramentoPedido.isEmpty()) {
            throw new PedidoNaoEncontradoException("Pedido não encontrado");
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

    public void deletarMonitoramentoDePedido(String pedidoId) {
        List<Monitoramento> monitoramentoPedido = buscarMonitoramentoPorPedidoId(pedidoId);
        monitoramentoRepository.deleteAll(monitoramentoPedido);
    }
}
