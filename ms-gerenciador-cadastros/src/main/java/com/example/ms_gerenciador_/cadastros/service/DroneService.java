package com.example.ms_gerenciador_.cadastros.service;

import com.example.ms_gerenciador_.cadastros.model.Drone;
import com.example.ms_gerenciador_.cadastros.repository.DroneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroneService {

    @Autowired
    private DroneRepository droneRepository;

    @Transactional
    public Drone cadastrarDrone(Drone drone) {
        return droneRepository.save(drone);
    }
}
