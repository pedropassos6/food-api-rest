//package com.pedro.foodapi.infrastructure.repository;
//
//import com.pedro.foodapi.domain.model.Restaurante;
//import com.pedro.foodapi.domain.repository.RestauranteRepository;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//import java.util.List;
//
//@Repository
//public class RestauranteRepositoryImpl implements RestauranteRepository {
//
//    @PersistenceContext
//    private EntityManager manager;
//
//    @Override
//    public List<Restaurante> listar() {
//        TypedQuery<Restaurante> query = manager.createQuery("from Restaurante", Restaurante.class);
//
//        return query.getResultList();
//    }
//
//    @Override
//    public Restaurante buscar(Long id) {
//        return manager.find(Restaurante.class, id);
//    }
//
//    @Override
//    @Transactional
//    public Restaurante salvar(Restaurante restaurante) {
//        return manager.merge(restaurante);
//    }
//
//    @Override
//    @Transactional
//    public void remover(Restaurante restaurante) {
//        restaurante = buscar(restaurante.getId());
//        manager.remove(restaurante);
//    }
//}
