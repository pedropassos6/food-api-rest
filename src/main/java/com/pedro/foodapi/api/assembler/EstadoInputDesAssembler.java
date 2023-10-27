package com.pedro.foodapi.api.assembler;

import com.pedro.foodapi.api.model.input.EstadoInput;
import com.pedro.foodapi.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoInputDesAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Estado toDomainObject(EstadoInput estadoInput){
        return modelMapper.map(estadoInput, Estado.class);
    }

    public void copyToDomainObject(EstadoInput estadoInput, Estado estado){
        modelMapper.map(estadoInput, estado);
    }
}
