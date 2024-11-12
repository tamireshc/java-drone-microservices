package com.example.ms_gerenciador_.cadastros.service;

import com.example.ms_gerenciador_.cadastros.dto.DroneRequestDTO;
import com.example.ms_gerenciador_.cadastros.exception.DroneNaoExistenteException;
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
    @Value("${rabbitmq.dronedisponivel.exchangeSinal}")
    private String enchangedDroneDisponivelSinal;

    @Transactional
    public Drone cadastrarDrone(DroneRequestDTO drone) {
        if (!StatusDrone.equals(drone.getStatus())) {
            throw new StatusInvalidoException("Status inexistente");
        }
        Drone novoDrone = new Drone();
        novoDrone.setModelo(drone.getModelo());
        novoDrone.setMarca(drone.getMarca());
        novoDrone.setAno(drone.getAno());
        novoDrone.setStatus(StatusDrone.valueOf(drone.getStatus().toUpperCase()));
        Drone droneCriado = droneRepository.save(novoDrone);

        if (droneCriado.getStatus().equals(StatusDrone.DISPONIVEL)) {
            enviarParaFilaService.enviarDroneDisponivelParaFila("START", enchangedDroneDisponivelSinal);
        }
        return droneCriado;
    }

    public Drone buscarDronePorId(String id) {
        Drone drone = droneRepository.findById(Long.parseLong(id)).orElse(null);
        if (drone == null) {
            throw new DroneNaoExistenteException("Drone não encontrado");
        }
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
        if (status.equalsIgnoreCase("DISPONIVEL")) {
            enviarParaFilaService.enviarDroneDisponivelParaFila("START", enchangedDroneDisponivelSinal);
        }
        return droneRepository.save(drone);
    }
}
