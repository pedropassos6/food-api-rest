package com.pedro.foodapi.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pedro.foodapi.api.model.mixin.CidadeMixin;
import com.pedro.foodapi.api.model.mixin.CozinhaMixin;
import com.pedro.foodapi.api.model.mixin.RestauranteMixin;
import com.pedro.foodapi.domain.model.Cidade;
import com.pedro.foodapi.domain.model.Cozinha;
import com.pedro.foodapi.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    //essa classe faz a ligação entre a classe model e a classe mixin, fazendo com que a duas se conversem

    private static final long serialVersionUID = 1L;

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
    }


}
