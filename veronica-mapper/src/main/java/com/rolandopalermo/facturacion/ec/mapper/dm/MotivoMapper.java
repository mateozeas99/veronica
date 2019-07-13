package com.rolandopalermo.facturacion.ec.mapper.dm;

import org.springframework.stereotype.Component;

import com.rolandopalermo.facturacion.ec.dto.v1_0.dm.MotivoDTO;
import com.rolandopalermo.facturacion.ec.mapper.Mapper;
import com.rolandopalermo.facturacion.ec.modelo.notadebito.Motivo;

@Component("motivoMapper")
public class MotivoMapper implements Mapper<MotivoDTO, Motivo> {

	@Override
	public Motivo convert(final MotivoDTO motivoDTO) {
		if (motivoDTO == null) {
			return null;
		}
		final Motivo motivo = new Motivo();
		motivo.setRazon(motivoDTO.getRazon());
		motivo.setValor(motivoDTO.getValor());
		return motivo;
	}

}