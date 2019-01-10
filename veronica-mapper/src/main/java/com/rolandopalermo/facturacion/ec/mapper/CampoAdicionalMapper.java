package com.rolandopalermo.facturacion.ec.mapper;

import org.springframework.stereotype.Component;

import com.rolandopalermo.facturacion.ec.dto.v1_0.CampoAdicionalDTO;
import com.rolandopalermo.facturacion.ec.modelo.CampoAdicional;

@Component("campoAdicionalMapper")
public class CampoAdicionalMapper implements Mapper<CampoAdicionalDTO, CampoAdicional> {

	@Override
	public CampoAdicional convert(final CampoAdicionalDTO campoAdicionalDTO) {
		if (campoAdicionalDTO == null) {
			return null;
		}
		final CampoAdicional campoAdicional = new CampoAdicional();
		campoAdicional.setNombre(campoAdicionalDTO.getNombre());
		campoAdicional.setValue(campoAdicionalDTO.getValue());
		return campoAdicional;
	}
	
}