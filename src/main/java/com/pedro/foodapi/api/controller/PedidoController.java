package com.pedro.foodapi.api.controller;

import com.google.common.collect.ImmutableMap;
import com.pedro.foodapi.api.assembler.PedidoInputDesAssembler;
import com.pedro.foodapi.api.assembler.PedidoModelAssembler;
import com.pedro.foodapi.api.assembler.PedidoResumoModelAssembler;
import com.pedro.foodapi.api.model.PedidoModel;
import com.pedro.foodapi.api.model.PedidoResumoModel;
import com.pedro.foodapi.api.model.input.PedidoInput;
import com.pedro.foodapi.core.data.PageableTranslator;
import com.pedro.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.pedro.foodapi.domain.exception.NegocioException;
import com.pedro.foodapi.domain.model.Pedido;
import com.pedro.foodapi.domain.model.Usuario;
import com.pedro.foodapi.domain.repository.PedidoRepository;
import com.pedro.foodapi.domain.filter.PedidoFilter;
import com.pedro.foodapi.domain.service.EmissaoPedidoService;
import com.pedro.foodapi.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    private PedidoInputDesAssembler pedidoInputDesAssembler;

    @GetMapping
    public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable){
        pageable = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);

        List<PedidoResumoModel> pedidosResumoModel = pedidoResumoModelAssembler.toCollection(pedidosPage.getContent());
        Page<PedidoResumoModel> pedidosResumoModelPage = new PageImpl<>(pedidosResumoModel, pageable, pedidosPage.getTotalElements());

        return pedidosResumoModelPage;
    }

//    @GetMapping
//    public MappingJacksonValue listar(){
//        List<Pedido> todosoPedidos = pedidoRepository.findAll();
//
//        return pedidoResumoModelAssembler.toCollection(todosoPedidos);
//    }

    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable String codigoPedido){
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigoPedido);

        return pedidoModelAssembler.toModel(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel adicionar(@Valid @RequestBody PedidoInput pedidoInput){
        try{
            Pedido novoPedido = pedidoInputDesAssembler.toDomainObject(pedidoInput);

            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedido.emitir(novoPedido);

            return pedidoModelAssembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private Pageable traduzirPageable(Pageable apiPageable){
        var mapeamento = ImmutableMap.of(
                "codigo", "codigo",
                "restaurante.nome", "restaurante.nome",
                "nomeCliente", "cliente.nome",
                "valorTotal", "valorTotal"
        );

        return PageableTranslator.translate(apiPageable, mapeamento);
    }
}
