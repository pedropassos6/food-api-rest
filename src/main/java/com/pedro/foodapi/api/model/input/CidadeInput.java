package com.pedro.foodapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CidadeInput {

    @NotBlank
    private String nome;

    @NotNull
    @Valid
    private EstadoIdInput estado;
}
