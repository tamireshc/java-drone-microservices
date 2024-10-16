package com.example.ms_gerenciador_.cadastros.exception;

public class UsuarioExistenteException extends RuntimeException {
    public UsuarioExistenteException(String message) {
        super(message);
    }
}
