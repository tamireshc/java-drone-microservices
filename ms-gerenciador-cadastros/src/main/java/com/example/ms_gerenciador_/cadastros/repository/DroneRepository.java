package com.example.ms_gerenciador_.cadastros.repository;

import com.example.ms_gerenciador_.cadastros.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, Long> {
}
