package com.rolandopalermo.facturacion.ec.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rolandopalermo.facturacion.ec.persistence.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
	
	public List<Invoice> findByAccessKeyAndIsDeleted(String accessKey, boolean isDeleted);
	
}