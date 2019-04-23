package com.rolandopalermo.facturacion.ec.service.v1_0;

public interface GenericOperationService<DOMAIN, DTO> {

	public void saveOrUpdate(DTO dtoObject);
	
	public DOMAIN mapTo(DTO dtoObject);
	
}