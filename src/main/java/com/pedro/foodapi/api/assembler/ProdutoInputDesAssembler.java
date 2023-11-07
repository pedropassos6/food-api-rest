package com.pedro.foodapi.api.assembler;

import com.pedro.foodapi.api.model.input.ProdutoInput;
import com.pedro.foodapi.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoInputDesAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Produto toDomainObject(ProdutoInput produtoInput){
        return modelMapper.map(produtoInput, Produto.class);
    }

    public void copyToDomainObject(ProdutoInput produtoInput, Produto produto){
        modelMapper.map(produtoInput, produto);
    }
}
