package com.pedro.foodapi.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pedro.foodapi.domain.model.Restaurante;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public abstract class CozinhaMixin {

    @JsonIgnore
    private List<Restaurante> restaurantes = new ArrayList<>();
}
