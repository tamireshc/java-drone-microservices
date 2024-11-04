package com.example.ms_gerenciador_pedidos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UsuarioNaoExistenteException.class)
    public ResponseEntity<String> handleUsuarioNaoExistenteException(UsuarioNaoExistenteException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(ServicoIndisponivelException.class)
    public ResponseEntity<String> handleServicoIndisponivelException2(ServicoIndisponivelException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
    }

    @ExceptionHandler(StatusInvalidoException.class)
    public ResponseEntity<String> handleStatusInvalidoException(StatusInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}


