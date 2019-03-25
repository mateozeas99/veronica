package com.rolandopalermo.facturacion.ec.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolandopalermo.facturacion.ec.persistence.entity.TaxType;

@Repository
public interface TaxTypeRepository extends JpaRepository<TaxType, Long> {
}