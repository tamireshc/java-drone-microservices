package com.example.ms_monitoramento.controller;

import com.example.ms_monitoramento.LatitudeLongitudeDTO;
import com.example.ms_monitoramento.model.Monitoramento;
import com.example.ms_monitoramento.service.MonitoramentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitor")
public class MonitoramentoController {
    @Autowired
    MonitoramentoService monitoramentoService;

    @GetMapping("/{pedidoId}")
    public ResponseEntity<List<Monitoramento>> buscarMonitoramentoPorPedidoId(@PathVariable String pedidoId) {
        List<Monitoramento> monitoramento = monitoramentoService.buscarMonitoramentoPorPedidoId(pedidoId);
        return ResponseEntity.ok(monitoramento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Monitoramento> editarPontoDeMonitoramento(
            @PathVariable String id,
            @RequestBody LatitudeLongitudeDTO latitudeLongitudeDTO
    ) {
        Monitoramento monitoramentoEditado = monitoramentoService.editarPontoDeMonitoramento(id, latitudeLongitudeDTO);
        return ResponseEntity.ok(monitoramentoEditado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPontoDeMonitoramento(@PathVariable String id) {
        monitoramentoService.deletarPontoDeMonitoramento(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/order/{pedidoId}")
    public ResponseEntity<Void> deletarMonitoramentoDePedido(@PathVariable String pedidoId) {
        monitoramentoService.deletarMonitoramentoDePedido(pedidoId);
        return ResponseEntity.noContent().build();
    }
}
