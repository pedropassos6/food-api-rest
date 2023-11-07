package com.pedro.foodapi.api.controller;

import com.pedro.foodapi.api.assembler.PedidoModelAssembler;
import com.pedro.foodapi.api.model.PedidoModel;
import com.pedro.foodapi.domain.model.Pedido;
import com.pedro.foodapi.domain.repository.PedidoRepository;
import com.pedro.foodapi.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @GetMapping
    public List<PedidoModel> listar(){
        List<Pedido> todosoPedidos = pedidoRepository.findAll();

        return pedidoModelAssembler.toCollection(todosoPedidos);
    }

    @GetMapping("/{pedidoId}")
    public PedidoModel buscar(@PathVariable Long pedidoId){
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        return pedidoModelAssembler.toModel(pedido);
    }
}
