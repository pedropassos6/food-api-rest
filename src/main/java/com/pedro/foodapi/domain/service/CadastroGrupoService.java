package com.pedro.foodapi.domain.service;

import com.pedro.foodapi.domain.exception.EntidadeEmUsoException;
import com.pedro.foodapi.domain.exception.GrupoNaoEncontradoException;
import com.pedro.foodapi.domain.model.Grupo;
import com.pedro.foodapi.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class CadastroGrupoService {

    private static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso";

    @Autowired
    private GrupoRepository grupoRepository;

    @Transactional
    public Grupo salvar(@PathVariable Grupo grupo){
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void excluir(Long id){
        try{
            grupoRepository.deleteById(id);
            grupoRepository.flush();
        }catch (EmptyResultDataAccessException e){
            throw new GrupoNaoEncontradoException(id);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format(MSG_GRUPO_EM_USO, id));
        }
    }

    public Grupo buscarOuFalhar(Long id){
        return grupoRepository.findById(id)
                .orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }
}
