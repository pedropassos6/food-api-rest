package com.pedro.foodapi.api.controller;

import com.pedro.foodapi.domain.model.Cozinha;
import com.pedro.foodapi.domain.model.Restaurante;
import com.pedro.foodapi.domain.repository.CozinhaRepository;
import com.pedro.foodapi.domain.repository.RestauranteRepository;
import com.pedro.foodapi.infrastructure.repository.spec.RestauranteSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> cozinhasPorNome(@RequestParam String nome){
        return cozinhaRepository.findTodasByNomeContaining(nome);
    }

    @GetMapping("/cozinhas/unica-por-nome")
    public Optional<Cozinha> cozinhaPorNome(@RequestParam String nome){
        return cozinhaRepository.findByNome(nome);
    }

    @GetMapping("/restaurantes/por-taxa-frete")
    public List<Restaurante> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes/por-nome")
    public List<Restaurante> restaurantesPorTaxaFrete(String nome, Long cozinhaId){
        return restauranteRepository.consultarPorNome(nome, cozinhaId);
    }

    @GetMapping("/restaurantes/por-nome-e-frete")
    public List<Restaurante> restaurantesPorNomeFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
    }

    @GetMapping("/restaurantes/primeiro-por-nome")
    public Optional<Restaurante> restaurantesPorTaxaFrete(String nome){
        return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/com-frete-gratis")
    public List<Restaurante> restaurantesComFreteGratis(String nome){
//        var comFreteGratis = new RestauranteComFreteGratisSpec();
//        var comNomeSemelhante = new RestauranteComFreteGratisSpec();

        return restauranteRepository.findComFreteGratis(nome);
    }

    @GetMapping("/restaurantes/primeiro")
    public Optional<Restaurante> restaurantePrimeiro(){
        return restauranteRepository.buscarPrimeiro();
    }

    @GetMapping("/cozinhas/primeiro")
    public Optional<Cozinha> cozinhaPrimeiro(){
        return cozinhaRepository.buscarPrimeiro();
    }




}