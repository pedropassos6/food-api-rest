package com.pedro.foodapi.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.pedro.foodapi.api.model.view.RestauranteView;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaModel {

    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    @JsonView(RestauranteView.Resumo.class)
    private String nome;
}
