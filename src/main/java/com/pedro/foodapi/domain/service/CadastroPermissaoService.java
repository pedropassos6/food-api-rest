package com.pedro.foodapi.domain.service;

import com.pedro.foodapi.domain.exception.PermissaoNaoEncontradaException;
import com.pedro.foodapi.domain.model.Permissao;
import com.pedro.foodapi.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroPermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao buscarOuFalhar(Long id){
        return permissaoRepository.findById(id)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(id));
    }

}
