package com.pedro.foodapi.domain.repository;

import com.pedro.foodapi.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {


    List<Cozinha> findTodasByNomeContaining(String nome);

    Optional<Cozinha> findByNome(String nome);

    boolean existsByNome(String nome);

}
