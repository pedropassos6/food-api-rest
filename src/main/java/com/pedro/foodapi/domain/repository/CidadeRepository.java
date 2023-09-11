package com.pedro.foodapi.domain.repository;

import com.pedro.foodapi.domain.model.Cidade;

import java.util.List;

public interface CidadeRepository {

    List<Cidade> listar();
    Cidade buscar(Long id);
    Cidade salvar(Cidade cidade);
    void remover(Long id);
}
