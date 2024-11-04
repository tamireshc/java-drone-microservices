package com.example.ms_gerenciador_pedidos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoResponseDTO {
    private String logradouro;
    private int numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}
