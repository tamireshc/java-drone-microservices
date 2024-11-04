package com.example.ms_gerenciador_pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnderecoDTO {
    private Long id;
    private String logradouro;
    private int numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String latitude;
    private String longitude;

    public EnderecoResponseDTO toEnderecoResponseDTO(EnderecoDTO endereco) {
        EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO();
        enderecoResponseDTO.setLogradouro(endereco.getLogradouro());
        enderecoResponseDTO.setNumero(endereco.getNumero());
        enderecoResponseDTO.setComplemento(endereco.getComplemento());
        enderecoResponseDTO.setBairro(endereco.getBairro());
        enderecoResponseDTO.setCidade(endereco.getCidade());
        enderecoResponseDTO.setEstado(endereco.getEstado());
        enderecoResponseDTO.setCep(endereco.getCep());
        return enderecoResponseDTO;
    }
}
