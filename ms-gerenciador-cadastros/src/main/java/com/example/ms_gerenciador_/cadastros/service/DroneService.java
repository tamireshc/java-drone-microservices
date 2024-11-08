package com.example.ms_gerenciador_.cadastros.service;

import com.example.ms_gerenciador_.cadastros.exception.StatusInvalidoException;
import com.example.ms_gerenciador_.cadastros.model.Drone;
import com.example.ms_gerenciador_.cadastros.model.enums.StatusDrone;
import com.example.ms_gerenciador_.cadastros.repository.DroneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneService {

    @Autowired
    private DroneRepository droneRepository;
    @Autowired
    EnviarParaFilaService enviarParaFilaService;
    @Value("${rabbitmq.dronependente.exchangeSinal}")
    private String enchangedDronePendenteSinal;

    @Transactional
    public Drone cadastrarDrone(Drone drone) {
        Drone droneCriado = droneRepository.save(drone);
        enviarParaFilaService.enviarDroneDisponivelParaFila(droneCriado.getId(), enchangedDronePendenteSinal);
        return droneCriado;
    }

    public Drone buscarDronePorId(String id) {
        return droneRepository.findById(Long.parseLong(id)).orElse(null);
    }

    public List<Drone> listarDrones() {
        return droneRepository.findAll();
    }

    public List<Drone> listarDronesPorStatus(String status) {
        if (!StatusDrone.equals(status)) {
            throw new StatusInvalidoException("Status inexistente");
        }
        return droneRepository.findByStatus(StatusDrone.valueOf(status.toUpperCase()));
    }

    @Transactional
    public Drone alterarStatusDrone(String id, String status) {
        Drone drone = buscarDronePorId(id);
        drone.setStatus(StatusDrone.valueOf(status.toUpperCase()));
        if(status.equalsIgnoreCase("DISPONIVEL")){
            enviarParaFilaService.enviarDroneDisponivelParaFila(drone.getId(), enchangedDronePendenteSinal);
        }
        return droneRepository.save(drone);
    }
}
