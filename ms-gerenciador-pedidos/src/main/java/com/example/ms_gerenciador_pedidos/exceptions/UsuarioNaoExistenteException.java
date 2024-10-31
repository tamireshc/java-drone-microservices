package com.example.ms_gerenciador_pedidos.exceptions;

public class UsuarioNaoExistenteException extends RuntimeException {
    public UsuarioNaoExistenteException(String message) {
        super(message);
    }
}
