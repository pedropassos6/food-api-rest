package com.pedro.foodapi.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.pedro.foodapi.api.assembler.RestauranteModelAssembler;
import com.pedro.foodapi.api.assembler.RestauranteInputDesAssembler;
import com.pedro.foodapi.api.model.RestauranteModel;
import com.pedro.foodapi.api.model.input.RestauranteInput;
import com.pedro.foodapi.api.model.view.RestauranteView;
import com.pedro.foodapi.domain.exception.CidadeNaoEncontradaException;
import com.pedro.foodapi.domain.exception.CozinhaNaoEncontradaException;
import com.pedro.foodapi.domain.exception.NegocioException;
import com.pedro.foodapi.domain.exception.RestauranteNaoEncontradoException;
import com.pedro.foodapi.domain.model.Restaurante;
import com.pedro.foodapi.domain.repository.RestauranteRepository;
import com.pedro.foodapi.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteInputDesAssembler restauranteInputDesAssembler;

    @GetMapping
    public List<RestauranteModel> listar(){
        return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    @GetMapping(params = "projecao=resumo")
    @JsonView(RestauranteView.Resumo.class)
    public List<RestauranteModel> listarResumido(){
        return listar();
    }

    @GetMapping(params = "projecao=apenas-nome")
    @JsonView(RestauranteView.ApenasNome.class)
    public List<RestauranteModel> listarApenasNomes(){
        return listar();
    }

    @GetMapping("/{id}")
    public RestauranteModel buscar(@PathVariable Long id){
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);

        return restauranteModelAssembler.toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody  @Valid RestauranteInput restauranteInput){
        try{
//            Restaurante restaurante = toDomainObject(restauranteInput);
            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteInputDesAssembler.toDomainObject(restauranteInput)));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteModel atualizar(@RequestBody @Valid RestauranteInput restauranteInput, @PathVariable Long id){
        try{
            Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
            restauranteInputDesAssembler.copyToDomainObject(restauranteInput, restauranteAtual);

//            Restaurante restaurante = restauranteInputDesAssembler.toDomainObject(restauranteInput);
//            BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long id){
        cadastroRestaurante.ativar(id);
    }

    @DeleteMapping("/{id}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id){
        cadastroRestaurante.inativar(id);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@RequestBody List<Long> restauranteIds){
        try{
            cadastroRestaurante.ativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@RequestBody List<Long> restauranteIds){
        try{
            cadastroRestaurante.inativar(restauranteIds);
        } catch (RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }

    }

    @PutMapping("/{id}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long id){
        cadastroRestaurante.abrir(id);
    }

    @PutMapping("{id}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long id){
        cadastroRestaurante.fechar(id);
    }

//    @PatchMapping("/{id}")
//    public Restaurante atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request){
//        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
//
//        merge(campos, restauranteAtual, request);
//
//        return atualizar(restauranteAtual, id);
//    }

//    private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino, HttpServletRequest request){
//        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
//
//        try{
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//
//            Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
//
//            camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//                Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//                field.setAccessible(true);
//
//                Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//
//                System.out.println(nomePropriedade + " = " + valorPropriedade);
//
//                ReflectionUtils.setField(field, restauranteDestino, novoValor);
//            });
//
//        }catch(IllegalArgumentException e){
//            Throwable rootCause = ExceptionUtils.getRootCause(e);
//            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
//        }
//
//    }
}
