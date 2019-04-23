package com.rolandopalermo.facturacion.ec.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rolandopalermo.facturacion.ec.persistence.entity.CreditMemo;

@Repository("creditMemoRepository")
public interface CreditMemoRepository extends BaseSRIRepository<CreditMemo, Long> {
	
	@Override
	@Query("select cm.accessKey from CreditMemo cm where cm.supplierId = ?1 and cm.isDeleted = ?2")
	public List<String> findBySupplierIdAndIsDeleted(String supplierId, boolean isDeleted);
	
}