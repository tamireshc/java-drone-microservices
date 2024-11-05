package com.example.ms_gerenciador_pedidos.exceptions;

public class PedidoInexistenteException extends RuntimeException {
    public PedidoInexistenteException(String message) {
        super(message);
    }
}
