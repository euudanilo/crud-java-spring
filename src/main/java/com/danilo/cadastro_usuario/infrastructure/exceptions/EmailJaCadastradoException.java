package com.danilo.cadastro_usuario.infrastructure.exceptions;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String mensagem) {
        super(mensagem);
    }
}
