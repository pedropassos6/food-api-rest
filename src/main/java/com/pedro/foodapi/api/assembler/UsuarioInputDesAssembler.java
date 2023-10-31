package com.pedro.foodapi.api.assembler;

import com.pedro.foodapi.api.model.input.UsuarioInput;
import com.pedro.foodapi.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInputDesAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomainObject(UsuarioInput usuarioInput){
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyToDomainObject(UsuarioInput usuarioInput, Usuario usuario){
        modelMapper.map(usuarioInput, usuario);
    }
}
