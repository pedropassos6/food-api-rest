package com.pedro.foodapi.api.assembler;

import com.pedro.foodapi.api.model.input.GrupoInput;
import com.pedro.foodapi.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoInputDesAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo toDomainObject(GrupoInput grupoInput){
        return modelMapper.map(grupoInput, Grupo.class);
    }

    public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo){
        modelMapper.map(grupoInput, grupo);
    }
}
