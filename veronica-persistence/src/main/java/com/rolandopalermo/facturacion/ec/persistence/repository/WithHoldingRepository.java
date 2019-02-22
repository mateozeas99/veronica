package com.rolandopalermo.facturacion.ec.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolandopalermo.facturacion.ec.persistence.entity.WithHolding;

@Repository
public interface WithHoldingRepository extends JpaRepository<WithHolding, Long> {

	public List<WithHolding> findByAccessKeyAndIsDeleted(String accessKey, boolean isDeleted);

}