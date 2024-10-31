package com.example.ms_gerenciador_pedidos.exceptions;

public class ServicoIndisponivelException extends RuntimeException {
    public ServicoIndisponivelException(String message) {
        super(message);
    }
}
