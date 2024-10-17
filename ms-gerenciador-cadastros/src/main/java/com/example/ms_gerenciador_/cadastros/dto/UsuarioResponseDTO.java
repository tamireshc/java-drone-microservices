package com.example.ms_gerenciador_.cadastros.dto;

import com.example.ms_gerenciador_.cadastros.model.Usuario;
import jdk.jshell.Snippet;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;

    public UsuarioResponseDTO converterUsuarioparaUsuarioResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getTelefone(),
                usuario.getEmail());
    }
}
