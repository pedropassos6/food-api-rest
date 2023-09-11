package com.pedro.foodapi.domain.service;

import com.pedro.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.pedro.foodapi.domain.model.Cidade;
import com.pedro.foodapi.domain.model.Estado;
import com.pedro.foodapi.domain.repository.CidadeRepository;
import com.pedro.foodapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade){
        Long estadoId = cidade.getEstado().getId();

        Estado estado = estadoRepository.buscar(estadoId);

        if(estado == null){
            throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de estado com código %d", estadoId));
        }

        cidade.setEstado(estado);

        return cidadeRepository.salvar(cidade);
    }

    public void excluir(Long id){
        //TODO implementar metodo excluir aqui
    }
}
