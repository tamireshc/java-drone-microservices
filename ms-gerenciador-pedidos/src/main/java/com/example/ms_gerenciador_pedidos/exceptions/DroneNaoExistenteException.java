package com.example.ms_gerenciador_pedidos.exceptions;

public class DroneNaoExistenteException extends RuntimeException {
    public DroneNaoExistenteException(String message) {
        super(message);
    }
}
