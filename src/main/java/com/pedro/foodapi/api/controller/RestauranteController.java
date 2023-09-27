package com.pedro.foodapi.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedro.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.pedro.foodapi.domain.model.Restaurante;
import com.pedro.foodapi.domain.repository.RestauranteRepository;
import com.pedro.foodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @GetMapping
    public List<Restaurante> listar(){
        return restauranteRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long id){
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);

        return restaurante.map(value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante){
        try{
            restaurante = cadastroRestaurante.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@RequestBody Restaurante restaurante, @PathVariable Long id){
        try {
            Optional<Restaurante> restauranteAtual = restauranteRepository.findById(id);

            if (restauranteAtual.isPresent()) {
                BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
                Restaurante restauranteSalvo = cadastroRestaurante.salvar(restauranteAtual.get());
                return ResponseEntity.ok(restauranteSalvo);
            }
            return ResponseEntity.notFound().build();
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos){
        Optional<Restaurante> restauranteAtual = restauranteRepository.findById(id);
        if (restauranteAtual == null)
            return ResponseEntity.notFound().build();

        merge(campos, restauranteAtual.get());

        return atualizar(restauranteAtual.get(), id);
    }

    private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino){
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);

        camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            System.out.println(nomePropriedade + " = " + valorPropriedade);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }




}
