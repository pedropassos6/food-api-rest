package com.pedro.foodapi.api.controller;

import com.pedro.foodapi.api.assembler.CidadeInputDesAssembler;
import com.pedro.foodapi.api.assembler.CidadeModelAssembler;
import com.pedro.foodapi.api.model.CidadeModel;
import com.pedro.foodapi.api.model.input.CidadeInput;
import com.pedro.foodapi.domain.exception.EstadoNaoEncontradoException;
import com.pedro.foodapi.domain.exception.NegocioException;
import com.pedro.foodapi.domain.model.Cidade;
import com.pedro.foodapi.domain.repository.CidadeRepository;
import com.pedro.foodapi.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Autowired
    private CidadeInputDesAssembler cidadeInputDesAssembler;

    @GetMapping
    public List<CidadeModel> listar(){
        return cidadeModelAssembler.toColletionModel(cidadeRepository.findAll());
    }

    @GetMapping("/{id}")
    public CidadeModel buscar(@PathVariable Long id){
        return cidadeModelAssembler.toModel(cadastroCidade.buscarOuFalhar(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput){
        try{
            return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidadeInputDesAssembler.toDomainObject(cidadeInput)));
        } catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public CidadeModel atualizar(@RequestBody @Valid CidadeInput cidadeInput, @PathVariable Long id){
        try{
            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);
            cidadeInputDesAssembler.copyToDomainObject(cidadeInput, cidadeAtual);
            cidadeAtual = cadastroCidade.salvar(cidadeAtual);
            return cidadeModelAssembler.toModel(cadastroCidade.salvar(cidadeAtual));
        }catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> remover(@PathVariable Long id){
//        try{
//            cadastroCidade.excluir(id);
//            return ResponseEntity.noContent().build();
//        } catch (EntidadeNaoEncontradaException e){
//            return ResponseEntity.notFound().build();
//        } catch (EntidadeEmUsoException e){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
//        }
//
//    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        cadastroCidade.excluir(id);
    }


}
