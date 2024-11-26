package com.example.ms_monitoramento.controller;

import com.example.ms_monitoramento.model.Monitoramento;
import com.example.ms_monitoramento.service.MonitoramentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitor")
public class MonitoramentoController {
    @Autowired
    MonitoramentoService monitoramentoService;

    @GetMapping("/{droneId}/{pedidoId}")
    public ResponseEntity<Monitoramento> buscarMonitoramentoPorId(@PathVariable String droneId, @PathVariable String pedidoId) {
        Monitoramento monitoramento = monitoramentoService.buscarMonitoramentoPorId(droneId, pedidoId);
        return ResponseEntity.ok(monitoramento);
    }
}
