package com.rolandopalermo.facturacion.ec.mapper;

import org.springframework.stereotype.Component;

import com.rolandopalermo.facturacion.ec.dto.v1_0.TotalImpuestoDTO;
import com.rolandopalermo.facturacion.ec.modelo.notacredito.TotalImpuesto;

@Component("totalImpuestoNotaCreditoMapper")
public class TotalImpuestoNotaCreditoMapper implements Mapper<TotalImpuestoDTO, TotalImpuesto> {

	@Override
	public TotalImpuesto convert(final TotalImpuestoDTO totalImpuestoDTO) {
		if (totalImpuestoDTO == null) {
			return null;
		}
		final TotalImpuesto totalImpuesto = new TotalImpuesto();
		totalImpuesto.setCodigo(totalImpuestoDTO.getCodigo());
		totalImpuesto.setCodigoPorcentaje(totalImpuestoDTO.getCodigoPorcentaje());
		totalImpuesto.setBaseImponible(totalImpuestoDTO.getBaseImponible());
		totalImpuesto.setValor(totalImpuestoDTO.getValor());
		return totalImpuesto;
	}

}