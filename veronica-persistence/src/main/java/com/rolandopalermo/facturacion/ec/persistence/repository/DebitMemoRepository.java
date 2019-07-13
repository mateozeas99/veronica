package com.rolandopalermo.facturacion.ec.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rolandopalermo.facturacion.ec.persistence.entity.DebitMemo;

@Repository("debitMemoRepository")
public interface DebitMemoRepository extends BaseSRIRepository<DebitMemo, Long> {

	@Override
	@Query("select dm.accessKey from DebitMemo dm where dm.supplierId = ?1 and dm.isDeleted = ?2")
	public List<String> findBySupplierIdAndIsDeleted(String supplierId, boolean isDeleted);

}