package com.rolandopalermo.facturacion.ec.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolandopalermo.facturacion.ec.persistence.entity.Withholding;

@Repository
public interface WithholdingRepository extends JpaRepository<Withholding, Long> {

	public List<Withholding> findByAccessKeyAndIsDeleted(String accessKey, boolean isDeleted);

}