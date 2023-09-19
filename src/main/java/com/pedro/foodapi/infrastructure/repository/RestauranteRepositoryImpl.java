package com.pedro.foodapi.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.pedro.foodapi.domain.model.Restaurante;
import com.pedro.foodapi.domain.repository.RestauranteRepository;
import com.pedro.foodapi.domain.repository.RestauranteRepositoryQueries;
import com.pedro.foodapi.infrastructure.repository.spec.RestauranteSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;



@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    @Lazy
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

        if(StringUtils.hasLength(nome))
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));

        if(taxaFreteFinal != null)
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));

        if(taxaFreteFinal != null)
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));


        criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurante> query = manager.createQuery(criteria);
        return query.getResultList();

//        var jpql = new StringBuilder();
//        jpql.append("from Restaurante where 0 = 0 ");
//
//        var parametros = new HashMap<String, Object>();
//
//        if (StringUtils.hasLength(nome)) {
//            jpql.append("and nome like :nome ");
//            parametros.put("nome", "%" + nome + "%");
//        }
//
//        if (taxaFreteInicial != null) {
//            jpql.append("and taxaFrete >= :taxaInicial ");
//            parametros.put("taxaInicial", taxaFreteInicial);
//        }
//
//        if (taxaFreteFinal != null) {
//            jpql.append("and taxaFrete <= :taxaFinal ");
//            parametros.put("taxaFinal", taxaFreteFinal);
//        }
//
//        TypedQuery<Restaurante> query = manager
//                .createQuery(jpql.toString(), Restaurante.class);
//
//        parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
//
//        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(RestauranteSpecs.comFreteGratis()
                .and(RestauranteSpecs.comNomeSemelhante(nome)));
    }

}
