package com.pedro.foodapi.domain.service;

import com.pedro.foodapi.domain.exception.EntidadeEmUsoException;
import com.pedro.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.pedro.foodapi.domain.model.Estado;
import com.pedro.foodapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado){
        return estadoRepository.salvar(estado);
    }

    public void excluir(Long id){
        try{
            estadoRepository.remover(id);

        }catch(EmptyResultDataAccessException e){

            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de estado com código %d", id));

        }catch(DataIntegrityViolationException e){

            throw new EntidadeEmUsoException(String.format("Estado de código %d não pode ser removida pois está em uso", id));
        }
    }
}
