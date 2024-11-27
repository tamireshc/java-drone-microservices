package com.example.ms_monitoramento.controller;

import com.example.ms_monitoramento.LatitudeLongitudeDTO;
import com.example.ms_monitoramento.model.Monitoramento;
import com.example.ms_monitoramento.service.MonitoramentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{droneId}/{pedidoId}")
    public ResponseEntity<Monitoramento> editarMonitoramento(
            @PathVariable String droneId,
            @PathVariable String pedidoId,
            @RequestBody LatitudeLongitudeDTO latitudeLongitudeDTO
    ) {
        Monitoramento monitoramentoEditado = monitoramentoService.editarMonitoramento(droneId, pedidoId, latitudeLongitudeDTO);
        return ResponseEntity.ok(monitoramentoEditado);
    }
}
