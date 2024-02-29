package com.pedro.foodapi.api.controller;

import com.pedro.foodapi.api.assembler.CozinhaInputDesAssembler;
import com.pedro.foodapi.api.assembler.CozinhaModelAssembler;
import com.pedro.foodapi.api.model.CozinhaModel;
import com.pedro.foodapi.api.model.input.CozinhaInput;
import com.pedro.foodapi.domain.exception.EntidadeEmUsoException;
import com.pedro.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.pedro.foodapi.domain.model.Cozinha;
import com.pedro.foodapi.domain.repository.CozinhaRepository;
import com.pedro.foodapi.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

//@RestController essa anotação substitui a anotacao controller e responseBody
@Controller
@ResponseBody // sinaliza que a resposta dos metodos devem ser enviadas como resposta para os metodos HTTP
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CozinhaModelAssembler cozinhaModelAssembler;

    @Autowired
    private CozinhaInputDesAssembler cozinhaInputDesAssembler;

    @GetMapping
    public Page<CozinhaModel> listar(@PageableDefault(size = 10) Pageable pageable){
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        List<CozinhaModel> cozinhasModel = cozinhaModelAssembler.toColletionModel(cozinhasPage.getContent());

        Page<CozinhaModel> cozinhasModelPage = new PageImpl<>(cozinhasModel, pageable, cozinhasPage.getTotalElements());

        return cozinhasModelPage;
    }

//    @ResponseStatus(HttpStatus.OK) para atribuir um status para a resposta
    @GetMapping("/{id}")
    public CozinhaModel buscar(@PathVariable Long id){

        return cozinhaModelAssembler.toModel(cadastroCozinha.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput){
        Cozinha cozinha = cozinhaInputDesAssembler.toDomainObject(cozinhaInput);
        cozinha = cadastroCozinha.salvar(cozinha);
        return cozinhaModelAssembler.toModel(cozinha);
    }

    @PutMapping("/{id}")
    public CozinhaModel atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInput cozinhaInput){
        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);

        cozinhaInputDesAssembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
        cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);

        return cozinhaModelAssembler.toModel(cozinhaAtual);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Cozinha> remover(@PathVariable Long id){
//        try {
//            cadastroCozinha.excluir(id);
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//
//        }catch(EntidadeNaoEncontradaException e){
//            return ResponseEntity.notFound().build();
//
//        }catch (EntidadeEmUsoException e){
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        cadastroCozinha.excluir(id);

    }
}
