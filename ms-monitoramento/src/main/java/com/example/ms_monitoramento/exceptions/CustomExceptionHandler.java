package com.example.ms_monitoramento.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MonitoramentoNaoExistenteException.class)
    public ResponseEntity<String> handleMonitoramentoNaoExistenteException(MonitoramentoNaoExistenteException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    public ResponseEntity<String> handlePedidoNaoEncontradoExceptionException(PedidoNaoEncontradoException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}
