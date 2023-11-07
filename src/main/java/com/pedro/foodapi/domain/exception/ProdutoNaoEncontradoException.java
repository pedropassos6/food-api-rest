package com.pedro.foodapi.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException{


    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(Long restauranteId, Long produtoId){
        this(String.format("Não existe um cadastro de produto com código %d para restaurante de código %d", produtoId, restauranteId));
    }

}
