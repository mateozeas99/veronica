package com.rolandopalermo.facturacion.ec.service.v1_0.impl;

import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.dto.v1_0.EmpresaDTO;
import com.rolandopalermo.facturacion.ec.persistence.entity.Supplier;

@Service("supplierServiceImpl")
public class SupplierServiceImpl extends GenericCRUDServiceImpl<Supplier, EmpresaDTO> {

	@Override
	public Supplier mapTo(EmpresaDTO empresaDTO) {
		Supplier supplier = new Supplier();
		supplier.setBusinessName(empresaDTO.getRazonSocial());
		supplier.setIdNumber(empresaDTO.getNumeroIdentificacion());
		supplier.setIdType(empresaDTO.getTipoIdentificacion());
		supplier.setLogo(empresaDTO.getLogo());
		supplier.setDeleted(false);
		return supplier;
	}

	@Override
	public EmpresaDTO build(Supplier domainObject) {
		EmpresaDTO dto = new EmpresaDTO();
		dto.setLogo(domainObject.getLogo());
		dto.setNumeroIdentificacion(domainObject.getIdNumber());
		dto.setRazonSocial(domainObject.getBusinessName());
		dto.setTipoIdentificacion(domainObject.getIdType());
		return dto;
	}
	
}