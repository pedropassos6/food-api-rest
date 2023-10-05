package com.pedro.foodapi.domain.service;

import com.pedro.foodapi.domain.exception.EntidadeEmUsoException;
import com.pedro.foodapi.domain.exception.EstadoNaoEncontradoException;
import com.pedro.foodapi.domain.model.Estado;
import com.pedro.foodapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    public static final String MSG_EM_USO = "Estado de código %d não pode ser removida pois está em uso";

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado){
        return estadoRepository.save(estado);
    }


    public void excluir(Long id){
        try{
            estadoRepository.deleteById(id);

        }catch(EmptyResultDataAccessException e){
            throw new EstadoNaoEncontradoException(id);

        }catch(DataIntegrityViolationException e){
            throw new EntidadeEmUsoException
                    (String.format(MSG_EM_USO, id));
        }
    }

    public Estado buscarOuFalhar(Long id){
        return estadoRepository.findById(id)
                .orElseThrow(() -> new EstadoNaoEncontradoException(id));
    }
}
