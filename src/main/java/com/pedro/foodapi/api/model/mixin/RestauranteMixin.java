package com.pedro.foodapi.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pedro.foodapi.domain.model.Cozinha;
import com.pedro.foodapi.domain.model.Endereco;
import com.pedro.foodapi.domain.model.FormaPagamento;
import com.pedro.foodapi.domain.model.Produto;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class RestauranteMixin {

    //essa classe é para manter as anotações do jacksom, para determinar o padrão de representação dos objetos

    //essa anotação faz com que vc não consiga atualizar o nome da cozinha pela atualização do restaurante com o value nome, allowGetters permite que o nome da cozinha seja mostrado no corpo da respota
    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Cozinha cozinha;

    @JsonIgnore
    private Endereco endereco;

    @JsonIgnore
    private OffsetDateTime dataCadastro;

    @JsonIgnore
    private OffsetDateTime dataAtualizacao;

    @JsonIgnore
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    @JsonIgnore
    private List<Produto> produtos = new ArrayList<>();
}
