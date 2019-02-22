package com.rolandopalermo.facturacion.ec.mapper.sri;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.util.DateUtils;
import com.rolandopalermo.facturacion.ec.common.util.JaxbUtils;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.AutorizacionDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.MensajeDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaComprobanteDTO;
import com.rolandopalermo.facturacion.ec.mapper.Mapper;

import autorizacion.ws.sri.gob.ec.Mensaje;
import autorizacion.ws.sri.gob.ec.RespuestaComprobante;

@Component
public class RespuestaComprobanteMapper {

	private Mapper<Mensaje, MensajeDTO> mensajeRecepcionMapper;

	private static final Logger logger = LogManager.getLogger(RespuestaComprobanteMapper.class);

	public RespuestaComprobanteDTO convert(RespuestaComprobante respuestaComprobante) {
		RespuestaComprobanteDTO dto = new RespuestaComprobanteDTO();
		if (respuestaComprobante != null) {
			List<AutorizacionDTO> autorizaciones = new ArrayList<>();
			if (respuestaComprobante.getAutorizaciones() != null && respuestaComprobante.getAutorizaciones().getAutorizacion() != null) {
				autorizaciones = respuestaComprobante.getAutorizaciones().getAutorizacion().stream().map(autorizacion -> {
					String fechaAutorizacion = "";
					try {
						fechaAutorizacion = DateUtils.convertirGreggorianToDDMMYYYY(autorizacion.getFechaAutorizacion().toString());
					} catch (VeronicaException e) {
						logger.error("RespuestaComprobanteMapper", e);
					}
					AutorizacionDTO autorizacionDTO = AutorizacionDTO.builder()
							.comprobante(autorizacion.getComprobante())
							.ambiente(autorizacion.getAmbiente())
							.estado(autorizacion.getEstado())
							.numeroAutorizacion(autorizacion.getNumeroAutorizacion())
							.fechaAutorizacion(fechaAutorizacion)
							.mensajes(getMensajeRecepcionMapper().convertAll(autorizacion.getMensajes().getMensaje()))
							.build();
					return autorizacionDTO;
				}).collect(Collectors.toList());
				dto.setTimestamp(respuestaComprobante.getAutorizaciones().getAutorizacion().get(0).getFechaAutorizacion().toGregorianCalendar().getTimeInMillis());
			}
			dto.setClaveAccesoConsultada(respuestaComprobante.getClaveAccesoConsultada());
			dto.setNumeroComprobantes(respuestaComprobante.getNumeroComprobantes());
			dto.setAutorizaciones(autorizaciones);
			try {
				dto.setContentAsXML(JaxbUtils.marshallAsString(respuestaComprobante));
			} catch (Exception e) {
				logger.error("RespuestaComprobanteMapper", e);
			}
		}
		return dto;
	}

	public Mapper<Mensaje, MensajeDTO> getMensajeRecepcionMapper() {
		return mensajeRecepcionMapper;
	}

	@Autowired
    @Qualifier("mensajeAutorizacionMapper")
	public void setMensajeRecepcionMapper(Mapper<Mensaje, MensajeDTO> mensajeRecepcionMapper) {
		this.mensajeRecepcionMapper = mensajeRecepcionMapper;
	}

}