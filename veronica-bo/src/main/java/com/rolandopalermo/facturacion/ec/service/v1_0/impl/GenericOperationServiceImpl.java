package com.rolandopalermo.facturacion.ec.service.v1_0.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rolandopalermo.facturacion.ec.service.v1_0.GenericOperationService;

public abstract class GenericOperationServiceImpl<DOMAIN, DTO> implements GenericOperationService<DOMAIN, DTO> {

	@Autowired
	private JpaRepository<DOMAIN, Long> repository;

	@Override
	public void saveOrUpdate(DTO dtoObject) {
		repository.save(mapTo(dtoObject));
	}

	@Override
	public abstract DOMAIN mapTo(DTO domainObject);

}