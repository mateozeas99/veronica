package com.rolandopalermo.facturacion.ec.service.v1_0;

import com.rolandopalermo.facturacion.ec.common.exception.ResourceNotFoundException;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.dto.v1_0.ComprobanteDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.ComprobanteIdDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.ListaComprobantesDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaComprobanteDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaSolicitudDTO;
import com.rolandopalermo.facturacion.ec.modelo.Comprobante;
import com.rolandopalermo.facturacion.ec.persistence.entity.BaseSRIEntity;

public interface GenericSRIService<DTO extends ComprobanteDTO, MODEL extends Comprobante, DOMAIN extends BaseSRIEntity> {

	public ComprobanteIdDTO create(DTO domainObject) throws VeronicaException, ResourceNotFoundException;

	public RespuestaSolicitudDTO post(String accessKey);

	public RespuestaComprobanteDTO apply(String accessKey) throws ResourceNotFoundException, VeronicaException;

	public void delete(String accessKey);

	public String getXML(String accessKey);

	public byte[] getPDF(String accessKey) throws ResourceNotFoundException, VeronicaException;
	
	public ListaComprobantesDTO findAllBySupplierId(String supplierId);

	public DOMAIN toEntity(MODEL modelObject, String contentAsXML) throws VeronicaException;

}