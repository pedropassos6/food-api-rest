package com.pedro.foodapi.api.controller;

import com.pedro.foodapi.domain.exception.EntidadeEmUsoException;
import com.pedro.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.pedro.foodapi.domain.model.Cidade;
import com.pedro.foodapi.domain.repository.CidadeRepository;
import com.pedro.foodapi.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return cidadeRepository.listar();
    }

    public ResponseEntity<Cidade> buscar(@PathVariable Long id){
        Cidade cidade = cidadeRepository.buscar(id);
        if(cidade != null)
            return ResponseEntity.ok(cidade);

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Cidade cidade){
        try{
            cidade = cadastroCidade.salvar(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
        }catch(EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
//        return cidadeRepository.salvar(cidade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> atualizar(@RequestBody Cidade cidade, @PathVariable Long id){
        Cidade cidadeAtual = cidadeRepository.buscar(id);

        if(cidadeAtual != null){
            BeanUtils.copyProperties(cidade, cidadeAtual, "id");
            cidadeAtual = cadastroCidade.salvar(cidadeAtual);
            return ResponseEntity.ok(cidadeAtual);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id){
        try{
            cadastroCidade.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }
}
