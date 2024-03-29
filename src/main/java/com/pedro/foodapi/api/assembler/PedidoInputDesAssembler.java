package com.pedro.foodapi.api.assembler;

import com.pedro.foodapi.api.model.input.PedidoInput;
import com.pedro.foodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoInputDesAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido toDomainObject(PedidoInput pedidoInput){
        return modelMapper.map(pedidoInput, Pedido.class);
    }

    public void copyToDomainObject(PedidoInput pedidoInput, Pedido pedido){
        modelMapper.map(pedidoInput, pedido);
    }
}
