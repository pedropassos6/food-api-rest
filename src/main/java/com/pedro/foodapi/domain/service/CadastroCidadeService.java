package com.pedro.foodapi.domain.service;

import com.pedro.foodapi.domain.exception.CidadeNaoEncontradaException;
import com.pedro.foodapi.domain.exception.EntidadeEmUsoException;
import com.pedro.foodapi.domain.model.Cidade;
import com.pedro.foodapi.domain.model.Estado;
import com.pedro.foodapi.domain.repository.CidadeRepository;
import com.pedro.foodapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCidadeService {

    public static final String MSG_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstado;

    @Transactional
    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();

        Estado estado = cadastroEstado.buscarOuFalhar(estadoId);
//        Estado estado = estadoRepository.findById(estadoId)
//                .orElseThrow(() -> new EntidadeNaoEncontradaException
//                        (String.format(MSG_NAO_ENCONTRADA, estadoId)));

        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void excluir(Long id){
        try {
            cidadeRepository.deleteById(id);
        } catch(EmptyResultDataAccessException e){
            throw new CidadeNaoEncontradaException(id);

        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException
                    (String.format(MSG_EM_USO, id));
        }
    }

    public Cidade buscarOuFalhar(Long id){
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }


}
