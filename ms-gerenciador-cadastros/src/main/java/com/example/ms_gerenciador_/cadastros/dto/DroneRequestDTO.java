package com.example.ms_gerenciador_.cadastros.dto;

import com.example.ms_gerenciador_.cadastros.model.enums.StatusDrone;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DroneRequestDTO {
    private String modelo;
    private String marca;
    private String ano;
    private String status;
}
