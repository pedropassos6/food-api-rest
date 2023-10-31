package com.pedro.foodapi.api.controller;

import com.pedro.foodapi.api.assembler.GrupoInputDesAssembler;
import com.pedro.foodapi.api.assembler.GrupoModelAssembler;
import com.pedro.foodapi.api.model.GrupoModel;
import com.pedro.foodapi.api.model.input.GrupoInput;
import com.pedro.foodapi.domain.model.Grupo;
import com.pedro.foodapi.domain.repository.GrupoRepository;
import com.pedro.foodapi.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @Autowired
    private GrupoInputDesAssembler grupoInputDesAssembler;

    @GetMapping
    public List<GrupoModel> listar(){
        List<Grupo> todosGrupos = grupoRepository.findAll();

        return grupoModelAssembler.toCollectionModel(todosGrupos);
    }

    @GetMapping("/{id}")
    public GrupoModel buscar(@PathVariable Long id){
        Grupo grupo = cadastroGrupo.buscarOuFalhar(id);

        return grupoModelAssembler.toModel(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput){
        Grupo grupo = grupoInputDesAssembler.toDomainObject(grupoInput);

        grupo = cadastroGrupo.salvar(grupo);

        return grupoModelAssembler.toModel(grupo);
    }

    @PutMapping("{id}")
    public GrupoModel atualizar(@PathVariable Long id, @RequestBody @Valid GrupoInput grupoInput){
        Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(id);

        grupoInputDesAssembler.copyToDomainObject(grupoInput, grupoAtual);
        grupoAtual = cadastroGrupo.salvar(grupoAtual);

        return grupoModelAssembler.toModel(grupoAtual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        cadastroGrupo.excluir(id);
    }


}
