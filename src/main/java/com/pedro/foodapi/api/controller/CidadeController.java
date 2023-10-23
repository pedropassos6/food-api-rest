package com.pedro.foodapi.api.controller;

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

    @GetMapping
    public List<Cidade> listar(){
        return cidadeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cidade buscar(@PathVariable Long id){
        return cadastroCidade.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@RequestBody @Valid Cidade cidade){
        try{
            return cadastroCidade.salvar(cidade);
        } catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Cidade atualizar(@RequestBody @Valid Cidade cidade, @PathVariable Long id){
        Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);

        BeanUtils.copyProperties(cidade, cidadeAtual, "id");

        try{
            return cadastroCidade.salvar(cidadeAtual);
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
