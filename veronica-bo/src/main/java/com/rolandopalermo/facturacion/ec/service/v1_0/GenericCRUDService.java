package com.rolandopalermo.facturacion.ec.service.v1_0;

import java.util.List;

public interface GenericCRUDService<DOMAIN, DTO> {

	public void saveOrUpdate(DTO dtoObject);

	public List<DTO> findAll(DTO dtoObject);

	/**
	 * Allows to map a serializable object to a domain object
	 * @param dtoObject
	 * @return
	 */
	public DOMAIN mapTo(DTO dtoObject);
	
	/**
	 * Allows to serialize a domain object
	 * @param domainObject
	 * @return
	 */
	public DTO build(DOMAIN domainObject);

}