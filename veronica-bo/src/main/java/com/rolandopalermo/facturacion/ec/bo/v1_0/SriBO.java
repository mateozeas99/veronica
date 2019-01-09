package com.rolandopalermo.facturacion.ec.bo.v1_0;

import static com.rolandopalermo.facturacion.ec.common.util.Constantes.APPLIED;
import static com.rolandopalermo.facturacion.ec.common.util.Constantes.INVALID;
import static com.rolandopalermo.facturacion.ec.common.util.Constantes.POSTED;
import static com.rolandopalermo.facturacion.ec.common.util.Constantes.REJECTED;
import static com.rolandopalermo.facturacion.ec.common.util.Constantes.SRI_APPLIED;
import static com.rolandopalermo.facturacion.ec.common.util.Constantes.SRI_INVALID;
import static com.rolandopalermo.facturacion.ec.common.util.Constantes.SRI_REJECTED;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.common.exception.ResourceNotFoundException;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.util.JaxbUtils;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaComprobanteDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaSolicitudDTO;
import com.rolandopalermo.facturacion.ec.mapper.sri.RespuestaComprobanteMapper;
import com.rolandopalermo.facturacion.ec.mapper.sri.RespuestaSolicitudMapper;
import com.rolandopalermo.facturacion.ec.persistence.entity.Invoice;
import com.rolandopalermo.facturacion.ec.persistence.repository.InvoiceRepository;
import com.rolandopalermo.facturacion.ec.soap.client.AutorizacionComprobanteProxy;
import com.rolandopalermo.facturacion.ec.soap.client.EnvioComprobantesProxy;

import autorizacion.ws.sri.gob.ec.RespuestaComprobante;

@Service("sriBO")
public class SriBO {

	@Value("${sri.wsdl.recepcion}")
	private String wsdlRecepcion;

	@Value("${sri.wsdl.autorizacion}")
	private String wsdlAutorizacion;

	@Autowired
	private RespuestaComprobanteMapper respuestaComprobanteMapper;

	@Autowired
	private InvoiceRepository invoiceRepository;

	private static final Logger logger = Logger.getLogger(SriBO.class);

	@Autowired
	private RespuestaSolicitudMapper respuestaSolicitudMapper;

	public RespuestaSolicitudDTO enviarComprobante(String claveAcceso) throws ResourceNotFoundException {
		EnvioComprobantesProxy proxy;
		try {
			proxy = new EnvioComprobantesProxy(wsdlRecepcion);
		} catch (MalformedURLException e) {
			logger.error("enviarComprobante", e);
			throw new ResourceNotFoundException(String.format("El archivo WSDL en la ruta %s no está disponible", wsdlRecepcion));
		}
		List<Invoice> invoices = invoiceRepository.findByAccessKey(claveAcceso);
		if (invoices == null || invoices.isEmpty()) {
			throw new ResourceNotFoundException(String.format("No se pudo encontrar la factura con clave de acceso %s", claveAcceso));
		}
		Invoice invoice = invoices.get(0);
		RespuestaSolicitudDTO respuestaSolicitudDTO = respuestaSolicitudMapper.convert(proxy.enviarComprobante(invoice.getXmlContent().getBytes()));
		if (respuestaSolicitudDTO.getEstado().compareTo(SRI_REJECTED) == 0) {
			invoice.setInternalStatusId(REJECTED);
		} else {
			invoice.setInternalStatusId(POSTED);
		}
		invoiceRepository.save(invoice);
		return respuestaSolicitudDTO;
	}

	public RespuestaComprobanteDTO autorizarComprobante(String claveAcceso) throws ResourceNotFoundException, VeronicaException {
		AutorizacionComprobanteProxy proxy;
		try {
			proxy = new AutorizacionComprobanteProxy(wsdlAutorizacion);
		} catch (MalformedURLException e) {
			logger.error("autorizarComprobante", e);
			throw new ResourceNotFoundException(String.format("El archivo WSDL en la ruta %s no está disponible", wsdlAutorizacion));
		}
		List<Invoice> invoices = invoiceRepository.findByAccessKey(claveAcceso);
		if (invoices == null || invoices.isEmpty()) {
			throw new ResourceNotFoundException(String.format("No se pudo encontrar la factura con clave de acceso %s", claveAcceso));
		}
		final Invoice invoice = invoices.get(0);
		RespuestaComprobante respuestaComprobante = proxy.autorizacionIndividual(claveAcceso);
		respuestaComprobante.getAutorizaciones().getAutorizacion().forEach(autorizacion -> {
			if (autorizacion.getEstado().compareTo(SRI_APPLIED) == 0) {
				invoice.setInternalStatusId(APPLIED);
			} else if (autorizacion.getEstado().compareTo(SRI_INVALID) == 0) {
				invoice.setInternalStatusId(INVALID);
			}
		});
		try {
			String xmlAuthorization = JaxbUtils.marshallAsString(respuestaComprobante);
			invoice.setXmlAuthorization(xmlAuthorization);
			invoiceRepository.save(invoice);
		} catch (Exception e) {
			logger.error("autorizarComprobante", e);
			throw new VeronicaException(e.getMessage());
		}
		RespuestaComprobanteDTO respuestaComprobanteDTO = respuestaComprobanteMapper.convert(respuestaComprobante);
		return respuestaComprobanteDTO;
	}

}