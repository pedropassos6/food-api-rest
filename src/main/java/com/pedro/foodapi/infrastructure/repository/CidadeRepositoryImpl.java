//package com.pedro.foodapi.infrastructure.repository;
//
//import com.pedro.foodapi.domain.model.Cidade;
//import com.pedro.foodapi.domain.repository.CidadeRepository;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//
//@Repository
//public class CidadeRepositoryImpl implements CidadeRepository {
//
//    @PersistenceContext
//    private EntityManager manager;
//
//    @Override
//    public List<Cidade> listar() {
//        return manager.createQuery("from Cidade", Cidade.class)
//                .getResultList();
//    }
//
//    @Override
//    public Cidade buscar(Long id) {
//        return manager.find(Cidade.class, id);
//    }
//
//    @Transactional
//    @Override
//    public Cidade salvar(Cidade cidade) {
//        return manager.merge(cidade);
//    }
//
//    @Transactional
//    @Override
//    public void remover(Long id) {
//        Cidade cidade = buscar(id);
//        if(cidade == null)
//            throw new EmptyResultDataAccessException(1);
//
//        manager.remove(cidade);
//    }
//}
