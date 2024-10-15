package com.example.ms_gerenciador_.cadastros.model;

import com.example.ms_gerenciador_.cadastros.model.enums.StatusDrone;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String modelo;
    @Column(nullable = false)
    private String marca;
    private String ano;
    @Column(nullable = false)
    private StatusDrone status;
}
