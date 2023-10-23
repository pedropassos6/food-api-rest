package com.pedro.foodapi.api.controller;

import com.pedro.foodapi.domain.exception.EntidadeEmUsoException;
import com.pedro.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.pedro.foodapi.domain.model.Cozinha;
import com.pedro.foodapi.domain.repository.CozinhaRepository;
import com.pedro.foodapi.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<Cozinha> listar(){
        return cozinhaRepository.findAll();
    }

//    @ResponseStatus(HttpStatus.OK) para atribuir um status para a resposta
    @GetMapping("/{id}")
    public Cozinha buscar(@PathVariable Long id){
        return cadastroCozinha.buscarOuFalhar(id);

        //        Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
//
//
//        if (cozinha.isPresent())
//            return ResponseEntity.ok(cozinha.get());
//
//        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
        return cadastroCozinha.salvar(cozinha);
    }

    @PutMapping("/{id}")
    public Cozinha atualizar(@PathVariable Long id, @RequestBody @Valid Cozinha cozinha){
        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);

        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

        return cadastroCozinha.salvar(cozinhaAtual);
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
