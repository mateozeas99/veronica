package com.rolandopalermo.facturacion.ec.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rolandopalermo.facturacion.ec.persistence.entity.Bol;

@Repository("bolRepository")
public interface BolRepository extends BaseSRIRepository<Bol, Long> {

	@Override
	@Query("select b.accessKey from Bol b where b.supplierId = ?1 and b.isDeleted = ?2")
	public List<String> findBySupplierIdAndIsDeleted(String supplierId, boolean isDeleted);

}