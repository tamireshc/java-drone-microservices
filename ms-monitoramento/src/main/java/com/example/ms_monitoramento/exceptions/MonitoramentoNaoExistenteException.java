package com.example.ms_monitoramento.exceptions;

public class MonitoramentoNaoExistenteException extends RuntimeException {
    public MonitoramentoNaoExistenteException(String message) {
        super(message);
    }
}
