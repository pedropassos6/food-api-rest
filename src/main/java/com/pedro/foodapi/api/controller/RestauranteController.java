package com.pedro.foodapi.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedro.foodapi.domain.exception.CozinhaNaoEncontradaException;
import com.pedro.foodapi.domain.exception.NegocioException;
import com.pedro.foodapi.domain.model.Restaurante;
import com.pedro.foodapi.domain.repository.RestauranteRepository;
import com.pedro.foodapi.domain.service.CadastroRestauranteService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;


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
    public Restaurante buscar(@PathVariable Long id){
        return cadastroRestaurante.buscarOuFalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante adicionar(@RequestBody  @Valid Restaurante restaurante){
        try{
            return cadastroRestaurante.salvar(restaurante);
        } catch (CozinhaNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Restaurante atualizar(@RequestBody @Valid Restaurante restaurante, @PathVariable Long id){
        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);

        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

        try{
            return cadastroRestaurante.salvar(restauranteAtual);
        } catch (CozinhaNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public Restaurante atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request){
        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);

        merge(campos, restauranteAtual, request);

        return atualizar(restauranteAtual, id);
    }

    private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino, HttpServletRequest request){
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);

            Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);

            camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

                System.out.println(nomePropriedade + " = " + valorPropriedade);

                ReflectionUtils.setField(field, restauranteDestino, novoValor);
            });

        }catch(IllegalArgumentException e){
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(Long id){
        cadastroRestaurante.excluir(id);
    }




}
