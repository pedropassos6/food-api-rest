package com.pedro.foodapi.domain.service;

import com.pedro.foodapi.domain.exception.PedidoNaoEncontradoException;
import com.pedro.foodapi.domain.model.Pedido;
import com.pedro.foodapi.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscarOuFalhar(Long pedidoId){
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

}
