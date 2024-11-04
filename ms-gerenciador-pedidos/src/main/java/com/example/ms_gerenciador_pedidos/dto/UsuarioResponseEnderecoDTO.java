package com.example.ms_gerenciador_pedidos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioResponseEnderecoDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String Telefone;
    private String email;
    private List<EnderecoDTO> enderecos;

    public UsuarioResponseEnderecoDTO(String serviçoIndisponívelNoMomento) {
    }
}
