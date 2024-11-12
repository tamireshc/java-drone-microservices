package com.example.ms_gerenciador_.cadastros.exception;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UsuarioExistenteException.class)
    public ResponseEntity<String> handleUsuarioExistenteException(UsuarioExistenteException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(UsuarioNaoExistenteException.class)
    public ResponseEntity<String> handleUsuarioNaoExistenteException(UsuarioNaoExistenteException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<String> handlePSQLException(PSQLException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Email, CPF ou CEP no formato incorreto");
    }

    @ExceptionHandler(StatusInvalidoException.class)
    public ResponseEntity<String> handleStatusInvalidoException(StatusInvalidoException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exception.getMessage());
    }

    @ExceptionHandler(EdicaoNaoPermitidaException.class)
    public ResponseEntity<String> handleEdicaoNaoPermitidaException(EdicaoNaoPermitidaException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exception.getMessage());
    }

    @ExceptionHandler(EnderecoNaoExistenteException.class)
    public ResponseEntity<String> handleEnderecoNaoExistenteException(EnderecoNaoExistenteException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(DroneNaoExistenteException.class)
    public ResponseEntity<String> handleDroneNaoExistenteException(DroneNaoExistenteException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
}
