package com.rolandopalermo.facturacion.ec.service.v1_0.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.service.v1_0.GenericCRUDService;

@Service
public abstract class GenericCRUDServiceImpl<DOMAIN, DTO> implements GenericCRUDService<DOMAIN, DTO> {

	@Autowired
	private JpaRepository<DOMAIN, Long> repository;

	@Override
	public void saveOrUpdate(DTO dtoObject) {
		DOMAIN domainObject = mapTo(dtoObject);
		repository.save(domainObject);
	}
	
	@Override
	public List<DTO> findAll(DTO dtoObject) {
		DOMAIN domainObject = mapTo(dtoObject);
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnorePaths("id");
		List<DOMAIN> lstObjs = repository.findAll(Example.of(domainObject, matcher));
		return lstObjs.stream()
				.map(obj -> build(obj))
				.collect(Collectors.toList());
	}

	@Override
	public abstract DOMAIN mapTo(DTO domainObject);

}