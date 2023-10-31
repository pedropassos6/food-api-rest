package com.pedro.foodapi.api.assembler;

//import com.pedro.foodapi.api.model.CozinhaModel;
import com.pedro.foodapi.api.model.RestauranteModel;
import com.pedro.foodapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteModel toModel(Restaurante restaurante) {

        return modelMapper.map(restaurante, RestauranteModel.class);

//        CozinhaModel cozinhaModel = new CozinhaModel();
//        cozinhaModel.setId(restaurante.getCozinha().getId());
//        cozinhaModel.setNome(restaurante.getCozinha().getNome());
//
//        RestauranteModel restauranteModel = new RestauranteModel();
//        restauranteModel.setId(restaurante.getId());
//        restauranteModel.setNome(restaurante.getNome());
//        restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
//        restauranteModel.setCozinha(cozinhaModel);
//        return restauranteModel;
    }

    public List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes){
        return restaurantes.stream()
                .map(restaurante -> toModel(restaurante))
                .collect(Collectors.toList());
    }
}
