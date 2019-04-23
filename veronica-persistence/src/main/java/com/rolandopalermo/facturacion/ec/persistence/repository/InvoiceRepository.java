package com.rolandopalermo.facturacion.ec.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rolandopalermo.facturacion.ec.persistence.entity.Invoice;

@Repository("invoiceRepository")
public interface InvoiceRepository extends BaseSRIRepository<Invoice, Long> {
	
	@Override
	@Query("select i.accessKey from Invoice i where i.supplierId = ?1 and i.isDeleted = ?2")
	public List<String> findBySupplierIdAndIsDeleted(String supplierId, boolean isDeleted);
	
}