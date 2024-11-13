package com.example.ms_gerenciador_pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;

    public UsuarioResponseDTO(UsuarioResponseEnderecoDTO remetente) {
    }

    public UsuarioResponseDTO converterUsuarioResponseEnderecoDTOparaUsuarioResponseDTO(UsuarioResponseEnderecoDTO usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getEmail(),
                usuario.getTelefone()
                );
    }
}
