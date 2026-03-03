package com.danilo.cadastro_usuario.infrastructure.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleUsuarioNaoEncontrado(
            UsuarioNaoEncontradoException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<Map<String, String>> handleEmailJaCadastrado(
            EmailJaCadastradoException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        LOGGER.log(Level.WARNING, "Violação de integridade: {0}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error", "Violação de integridade no banco de dados"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleErroGenerico(Exception ex) {

        LOGGER.log(Level.SEVERE, "Erro interno no servidor", ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Erro interno no servidor"));
    }
}
