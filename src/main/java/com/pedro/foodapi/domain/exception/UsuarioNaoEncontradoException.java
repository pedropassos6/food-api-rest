package com.pedro.foodapi.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException{

    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNaoEncontradoException(Long id){
        this(String.format("Não existe usuario cadastrado com código %d", id));
    }
}
