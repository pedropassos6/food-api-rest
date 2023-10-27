package com.pedro.foodapi.api.controller;

import com.pedro.foodapi.api.assembler.EstadoInputDesAssembler;
import com.pedro.foodapi.api.assembler.EstadoModelAssembler;
import com.pedro.foodapi.api.model.EstadoModel;
import com.pedro.foodapi.api.model.input.EstadoInput;
import com.pedro.foodapi.domain.exception.EntidadeEmUsoException;
import com.pedro.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.pedro.foodapi.domain.model.Estado;
import com.pedro.foodapi.domain.repository.EstadoRepository;
import com.pedro.foodapi.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @Autowired
    private EstadoModelAssembler estadoModelAssembler;

    @Autowired
    private EstadoInputDesAssembler estadoInputDesAssembler;

    @GetMapping
    public List<EstadoModel> listar(){
        List<Estado> todosEstados = estadoRepository.findAll();

        return estadoModelAssembler.toCollectionModel(todosEstados);
    }

    @GetMapping("/{id}")
    public EstadoModel buscar(@PathVariable Long id){
        Estado estado = cadastroEstado.buscarOuFalhar(id);
        return estadoModelAssembler.toModel(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoModel adicionar(@RequestBody EstadoInput estadoInput){
        Estado estado = estadoInputDesAssembler.toDomainObject(estadoInput);
        estado = cadastroEstado.salvar(estado);

        return estadoModelAssembler.toModel(estado);
    }

    @PutMapping("/{id}")
    public EstadoModel atualizar(@RequestBody EstadoInput estadoInput, @PathVariable Long id){
        Estado estadoAtual = cadastroEstado.buscarOuFalhar(id);
        estadoInputDesAssembler.copyToDomainObject(estadoInput, estadoAtual);

        estadoAtual = cadastroEstado.salvar(estadoAtual);
        return estadoModelAssembler.toModel(estadoAtual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        cadastroEstado.excluir(id);
    }



}
