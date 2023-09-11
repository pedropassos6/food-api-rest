package com.pedro.foodapi.api.controller;

import com.pedro.foodapi.domain.model.Cozinha;
import com.pedro.foodapi.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

//    @GetMapping("/cozinhas/por-nome")
//    public List<Cozinha> cozinhaPorNome(@RequestParam String nome){
//        return cozinhaRepository.consultarPorNome(nome);
//    }
}
