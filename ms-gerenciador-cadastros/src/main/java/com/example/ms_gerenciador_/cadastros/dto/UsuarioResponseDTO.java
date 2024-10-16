package com.example.ms_gerenciador_.cadastros.dto;

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

}
