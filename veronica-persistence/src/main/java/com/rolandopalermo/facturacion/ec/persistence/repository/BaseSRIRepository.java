package com.rolandopalermo.facturacion.ec.persistence.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseSRIRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

	public List<T> findByAccessKeyAndIsDeleted(String accessKey, boolean isDeleted);
	
	public List<String> findBySupplierIdAndIsDeleted(String supplierId, boolean isDeleted);

}