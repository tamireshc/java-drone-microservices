package com.example.ms_gerenciador_.cadastros.repository;

import com.example.ms_gerenciador_.cadastros.model.Drone;
import com.example.ms_gerenciador_.cadastros.model.enums.StatusDrone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    public List<Drone> findByStatus(StatusDrone status);
}
