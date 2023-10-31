package com.pedro.foodapi.api.assembler;

import com.pedro.foodapi.api.model.input.RestauranteInput;
import com.pedro.foodapi.domain.model.Cidade;
import com.pedro.foodapi.domain.model.Cozinha;
import com.pedro.foodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDesAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInput restauranteInput){

        return modelMapper.map(restauranteInput, Restaurante.class);

//        Restaurante restaurante = new Restaurante();
//        restaurante.setNome(restauranteInput.getNome());
//        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
//
//        Cozinha cozinha = new Cozinha();
//        cozinha.setId(restauranteInput.getCozinha().getId());
//
//        restaurante.setCozinha(cozinha);
//
//        return restaurante;
    }

    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante){
        restaurante.setCozinha(new Cozinha());
        /* esta instancia de cozinha é para que quando for atualizar o restaurante vc possa atualizar a cozinha
           o que faz com que vc sempre tenha que passar um id de cozinha no corpo da requisição
           caso contrario ele irá lançar uma exceção */

        if(restaurante.getEndereco() != null){
            restaurante.getEndereco().setCidade(new Cidade());
        }

        modelMapper.map(restauranteInput, restaurante);
    }
}
