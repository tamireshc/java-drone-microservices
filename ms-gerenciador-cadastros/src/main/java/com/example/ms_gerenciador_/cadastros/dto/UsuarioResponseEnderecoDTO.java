package com.example.ms_gerenciador_.cadastros.dto;

import com.example.ms_gerenciador_.cadastros.model.Endereco;
import com.example.ms_gerenciador_.cadastros.model.Usuario;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponseEnderecoDTO {
    private Long id;
    private String nome;
    private String email;
    private List<Endereco> enderecos;

    public UsuarioResponseEnderecoDTO converterUsuarioparaUsuarioResponseDTO(Usuario usuario) {
        return new UsuarioResponseEnderecoDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getEnderecos());
    }
}
