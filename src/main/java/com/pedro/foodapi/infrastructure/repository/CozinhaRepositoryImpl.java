//package com.pedro.foodapi.infrastructure.repository;
//
//import com.pedro.foodapi.domain.model.Cozinha;
//import com.pedro.foodapi.domain.repository.CozinhaRepository;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//import java.util.List;
//
//@Repository
//public class CozinhaRepositoryImpl implements CozinhaRepository {
//
//    @PersistenceContext
//    private EntityManager manager;
//
//    @Override
//    public List<Cozinha> listar(){
//        TypedQuery<Cozinha> query = manager.createQuery("from Cozinha", Cozinha.class);
//
//        return query.getResultList();
//    }
//
//    @Override
//    public List<Cozinha> consultarPorNome(String nome) {
//        return manager.createQuery("from Cozinha where nome = :nome")
//                .setParameter("nome", nome)
//                .getResultList();
//    }
//
//
//    @Override
//    public Cozinha buscar(Long id){
//        return manager.find(Cozinha.class, id);
//    }
//
//    @Override
//    @Transactional
//    public Cozinha salvar(Cozinha cozinha){
//        return manager.merge(cozinha);
//    }
//
//    @Override
//    @Transactional
//    public void remover (Long id){
//        Cozinha cozinha = buscar(id);
//
//        if (cozinha == null)
//            throw new EmptyResultDataAccessException(1);
//
//
//        manager.remove(cozinha);
//    }
//}
