package com.example.ms_gerenciador_pedidos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemetenteDestinatarioEnderecoDTO {
    private UsuarioResponseDTO remetente;
    private UsuarioResponseDTO destinatario;
    private EnderecoResponseDTO endereco;
}
