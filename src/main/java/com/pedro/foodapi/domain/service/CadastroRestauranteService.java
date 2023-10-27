package com.pedro.foodapi.domain.service;

import com.pedro.foodapi.domain.exception.RestauranteNaoEncontradoException;
import com.pedro.foodapi.domain.model.Cozinha;
import com.pedro.foodapi.domain.model.Restaurante;
import com.pedro.foodapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CadastroRestauranteService {

    public static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido pois está em uso";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Transactional
    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

//    @Transactional
//    public void excluir(Long id){
//        try {
//            restauranteRepository.deleteById(id);
//        }catch (EmptyResultDataAccessException e){
//            throw new RestauranteNaoEncontradoException(id);
//        }catch (DataIntegrityViolationException e){
//            throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, id));
//        }
//    }

    @Transactional
    public void ativar(Long id){
        Restaurante restauranteAtual = buscarOuFalhar(id);
        restauranteAtual.ativar();
    }

    @Transactional
    public void inativar(Long id){
        Restaurante restauranteAtual = buscarOuFalhar(id);
        restauranteAtual.inativar();
    }

    public Restaurante buscarOuFalhar(Long id){
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }


}
