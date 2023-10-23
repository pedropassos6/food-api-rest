package com.pedro.foodapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pedro.foodapi.core.validation.Groups;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity //anotação do JPA que representa uma entidade, que representa uma tabela no banco de dados
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cozinha {

    @NotNull(groups = Groups.CozinhaId.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;


    @NotBlank
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "cozinha")
    private List<Restaurante> restaurantes = new ArrayList<>();
}
