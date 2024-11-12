package com.example.ms_gerenciador_.cadastros.controller;

import com.example.ms_gerenciador_.cadastros.dto.DroneRequestDTO;
import com.example.ms_gerenciador_.cadastros.model.Drone;
import com.example.ms_gerenciador_.cadastros.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/register")
public class DroneController {

    @Autowired
    DroneService droneService;

    @PostMapping("/drone")
    public ResponseEntity<Drone> cadastrarDrone(@RequestBody DroneRequestDTO drone) {
        Drone droneResponse = droneService.cadastrarDrone(drone);
        return ResponseEntity.created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(droneResponse.getId())
                                .toUri())
                .body(droneResponse);
    }

    @GetMapping("/drone/{id}")
    public ResponseEntity<Drone> buscarDronePorId(@PathVariable String id) {
        Drone drone = droneService.buscarDronePorId(id);
        return ResponseEntity.ok(drone);
    }

    @GetMapping("/drone")
    public ResponseEntity<List<Drone>> listarDrones() {
        return ResponseEntity.ok(droneService.listarDrones());
    }

    @GetMapping("/drone/status/{status}")
    public ResponseEntity<List<Drone>> listarDronesPorStatus(@PathVariable String status) {
        return ResponseEntity.ok(droneService.listarDronesPorStatus(status));
    }

    @PutMapping("/drone/{id}/status/{status}")
    public ResponseEntity<Drone> alterarStatusDrone(@PathVariable String id, @PathVariable String status) {
        Drone drone = droneService.alterarStatusDrone(id, status);
        return ResponseEntity.ok(drone);
    }
}
