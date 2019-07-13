package com.rolandopalermo.facturacion.ec.mapper.dm;

import org.springframework.stereotype.Component;

import com.rolandopalermo.facturacion.ec.dto.v1_0.ImpuestoDTO;
import com.rolandopalermo.facturacion.ec.mapper.Mapper;
import com.rolandopalermo.facturacion.ec.modelo.notadebito.Impuesto;

@Component("impuestoNotaDebitoMapper")
public class ImpuestoMapper implements Mapper<ImpuestoDTO, Impuesto> {

	@Override
	public Impuesto convert(final ImpuestoDTO impuestoDTO) {
		if (impuestoDTO == null) {
			return null;
		}
		final Impuesto impuesto = new Impuesto();
		impuesto.setCodigo(impuestoDTO.getCodigo());
		impuesto.setCodigoPorcentaje(impuestoDTO.getCodigoPorcentaje());
		impuesto.setTarifa(impuestoDTO.getTarifa());
		impuesto.setBaseImponible(impuestoDTO.getBaseImponible());
		impuesto.setValor(impuestoDTO.getValor());
		return impuesto;
	}

}