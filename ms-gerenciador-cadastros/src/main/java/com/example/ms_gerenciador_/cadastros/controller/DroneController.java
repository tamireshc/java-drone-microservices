package com.example.ms_gerenciador_.cadastros.controller;

import com.example.ms_gerenciador_.cadastros.model.Drone;
import com.example.ms_gerenciador_.cadastros.repository.DroneRepository;
import com.example.ms_gerenciador_.cadastros.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/register")
public class DroneController {

    @Autowired
    DroneService droneService;

    @PostMapping("/drone")
    public ResponseEntity<Drone> cadastrarDrone(@RequestBody Drone drone) {
        Drone droneResponse = droneService.cadastrarDrone(drone);
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(droneResponse.getId())
                        .toUri())
                .body(droneResponse);
    }
}
