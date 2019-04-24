package com.rolandopalermo.facturacion.ec.service.v1_0;

import java.util.List;

public interface GenericCRUDService<DOMAIN, DTO> {

	public void saveOrUpdate(DTO dtoObject);

	public List<DTO> findAll(DTO dtoObject);

	public DOMAIN mapTo(DTO dtoObject);
	
	public DTO build(DOMAIN domainObject);

}