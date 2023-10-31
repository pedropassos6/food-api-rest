package com.pedro.foodapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UsuarioPasswordInput extends UsuarioInput{

    @NotBlank
    private String senha;
}
