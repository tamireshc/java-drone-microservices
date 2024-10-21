package com.example.ms_gerenciador_.cadastros.dto;

import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class EnderecoRequestDTO {

    private String logradouro;
    private int numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String usuarioId;

}
