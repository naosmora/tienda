package com.tienda.repository;

import com.tienda.domain.Constante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstanteRepository extends JpaRepository<Constante, Integer> {

    Constante findByAtributo(String atributo);
}