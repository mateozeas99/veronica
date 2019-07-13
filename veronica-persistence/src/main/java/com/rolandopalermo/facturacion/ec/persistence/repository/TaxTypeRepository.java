package com.rolandopalermo.facturacion.ec.persistence.repository;

import com.rolandopalermo.facturacion.ec.persistence.entity.TaxType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxTypeRepository extends JpaRepository<TaxType, Long> {

    public Optional<TaxType> findByCode(String code);

}