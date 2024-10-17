package com.example.ms_gerenciador_.cadastros.dto;

import com.example.ms_gerenciador_.cadastros.model.Endereco;
import com.example.ms_gerenciador_.cadastros.model.Usuario;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponseEnderecoDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String Telefone;
    private String email;
    private List<Endereco> enderecos;

    public UsuarioResponseEnderecoDTO converterUsuarioparaUsuarioResponseDTO(Optional<Usuario> usuario) {
        return new UsuarioResponseEnderecoDTO(
                usuario.get().getId(),
                usuario.get().getNome(),
                usuario.get().getSobrenome(),
                usuario.get().getTelefone(),
                usuario.get().getEmail(),
                usuario.get().getEnderecos());
    }
}
