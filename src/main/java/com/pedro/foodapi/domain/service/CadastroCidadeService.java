package com.pedro.foodapi.domain.service;

import com.pedro.foodapi.domain.exception.EntidadeEmUsoException;
import com.pedro.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.pedro.foodapi.domain.model.Cidade;
import com.pedro.foodapi.domain.model.Estado;
import com.pedro.foodapi.domain.repository.CidadeRepository;
import com.pedro.foodapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();

        Optional<Estado> estado = estadoRepository.findById(estadoId);

        if(estado == null){
            throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de estado com código %d", estadoId));
        }

        cidade.setEstado(estado.get());

        return cidadeRepository.save(cidade);
    }

    public void excluir(Long id){
        try {
            cidadeRepository.deleteById(id);
        } catch(EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cidade com código %d", id));
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format("Cidade de código %d não pode ser removida, pois está em uso", id));
        }


    }
}
