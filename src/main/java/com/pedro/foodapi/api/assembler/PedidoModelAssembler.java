package com.pedro.foodapi.api.assembler;

import com.pedro.foodapi.api.model.PedidoModel;
import com.pedro.foodapi.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoModel toModel(Pedido pedido){
        return modelMapper.map(pedido, PedidoModel.class);
    }

    public List<PedidoModel> toCollection(List<Pedido> pedidos){
        return pedidos.stream()
                .map(pedido -> toModel(pedido))
                .collect(Collectors.toList());
    }


}
