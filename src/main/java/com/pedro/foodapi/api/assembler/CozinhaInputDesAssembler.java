package com.pedro.foodapi.api.assembler;

import com.pedro.foodapi.api.model.input.CozinhaInput;
import com.pedro.foodapi.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaInputDesAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaInput cozinhaInput){
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaInput cozinhaInput, Cozinha cozinha){
        modelMapper.map(cozinhaInput, cozinha);
    }
}
