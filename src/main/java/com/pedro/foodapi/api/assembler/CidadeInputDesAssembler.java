package com.pedro.foodapi.api.assembler;

import com.pedro.foodapi.api.model.input.CidadeInput;
import com.pedro.foodapi.domain.model.Cidade;
import com.pedro.foodapi.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDesAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeInput cidadeInput){
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade){
        cidade.setEstado(new Estado());

        modelMapper.map(cidadeInput, cidade);
    }
}
