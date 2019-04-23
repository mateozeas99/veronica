package com.rolandopalermo.facturacion.ec.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rolandopalermo.facturacion.ec.persistence.entity.Withholding;

@Repository("withholdingRepository")
public interface WithholdingRepository extends BaseSRIRepository<Withholding, Long> {
	
	@Override
	@Query("select wh.accessKey from Withholding wh where wh.supplierId = ?1 and wh.isDeleted = ?2")
	public List<String> findBySupplierIdAndIsDeleted(String supplierId, boolean isDeleted);
	
}