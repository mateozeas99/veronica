package com.rolandopalermo.facturacion.ec.service.v1_0.impl;

import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.dto.v1_0.TipoImpuestoDTO;
import com.rolandopalermo.facturacion.ec.persistence.entity.TaxType;

@Service("taxTypeServiceImpl")
public class TaxTypeServiceImpl extends GenericCRUDServiceImpl<TaxType, TipoImpuestoDTO> {

	@Override
	public TaxType mapTo(TipoImpuestoDTO tipoImpuestoDTO) {
		TaxType taxType = new TaxType();
		taxType.setCode(tipoImpuestoDTO.getCodigo());
		taxType.setDescription(tipoImpuestoDTO.getDescripcion());
		return taxType;
	}

	@Override
	public TipoImpuestoDTO build(TaxType domainObject) {
		return null;
	}

}