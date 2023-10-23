package com.pedro.foodapi.domain.model;

import com.pedro.foodapi.core.validation.Groups;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cidade {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nome;

    @ManyToOne
    @JoinColumn(nullable = false)
    @ConvertGroup(from = Default.class, to = Groups.EstadoId.class)
    @NotNull
    @Valid
    private Estado estado;
}
