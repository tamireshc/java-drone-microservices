package com.example.ms_gerenciador_pedidos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PedidoResponseDTO {
    private Long id;
    private LocalDateTime dataPedido;
    private LocalDateTime dataEntrega;
    private String status;
    private EnderecoResponseDTO endereco;
    private UsuarioResponseDTO remetente;
    private UsuarioResponseDTO destinatario;
    private Long droneId;

    public PedidoResponseDTO(Long id, LocalDateTime dataPedido, String status,
                             EnderecoResponseDTO endereco, UsuarioResponseDTO remetente,
                             UsuarioResponseDTO destinatario) {
        this.id = id;
        this.dataPedido = dataPedido;
        this.status = status;
        this.endereco = endereco;
        this.remetente = remetente;
        this.destinatario = destinatario;
    }
}
