package com.rolandopalermo.facturacion.ec.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolandopalermo.facturacion.ec.persistence.entity.ReceiptType;

import java.util.Optional;

@Repository
public interface ReceiptTypeRepository extends JpaRepository<ReceiptType, Long> {

    public Optional<ReceiptType> findByCode(String code);

}