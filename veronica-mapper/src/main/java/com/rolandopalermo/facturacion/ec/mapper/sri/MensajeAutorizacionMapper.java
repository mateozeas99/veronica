package com.rolandopalermo.facturacion.ec.mapper.sri;

import org.springframework.stereotype.Component;

import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.MensajeDTO;
import com.rolandopalermo.facturacion.ec.mapper.Mapper;

import autorizacion.ws.sri.gob.ec.Mensaje;

@Component("mensajeAutorizacionMapper")
public class MensajeAutorizacionMapper implements Mapper<Mensaje, MensajeDTO> {

	@Override
	public MensajeDTO convert(final Mensaje mensaje) {
		if (mensaje == null) {
			return null;
		}
		final MensajeDTO mensajeDTO = new MensajeDTO();
		mensajeDTO.setIdentificador(mensaje.getIdentificador());
		mensajeDTO.setInformacionAdicional(mensaje.getInformacionAdicional());
		mensajeDTO.setMensaje(mensaje.getMensaje());
		mensajeDTO.setTipo(mensaje.getTipo());
		return mensajeDTO;
	}

}