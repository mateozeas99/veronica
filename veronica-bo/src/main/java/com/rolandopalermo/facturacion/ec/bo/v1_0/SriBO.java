package com.rolandopalermo.facturacion.ec.bo.v1_0;

import static com.rolandopalermo.facturacion.ec.common.util.Constantes.APPLIED;
import static com.rolandopalermo.facturacion.ec.common.util.Constantes.INVALID;
import static com.rolandopalermo.facturacion.ec.common.util.Constantes.POSTED;
import static com.rolandopalermo.facturacion.ec.common.util.Constantes.REJECTED;
import static com.rolandopalermo.facturacion.ec.common.util.Constantes.SRI_APPLIED;
import static com.rolandopalermo.facturacion.ec.common.util.Constantes.SRI_REJECTED;

import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.common.exception.ResourceNotFoundException;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.util.DateUtils;
import com.rolandopalermo.facturacion.ec.common.util.JaxbUtils;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaComprobanteDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaSolicitudDTO;
import com.rolandopalermo.facturacion.ec.mapper.sri.RespuestaComprobanteMapper;
import com.rolandopalermo.facturacion.ec.mapper.sri.RespuestaSolicitudMapper;
import com.rolandopalermo.facturacion.ec.modelo.factura.Factura;
import com.rolandopalermo.facturacion.ec.persistence.entity.Invoice;
import com.rolandopalermo.facturacion.ec.persistence.repository.InvoiceRepository;
import com.rolandopalermo.facturacion.ec.soap.client.AutorizacionComprobanteProxy;
import com.rolandopalermo.facturacion.ec.soap.client.EnvioComprobantesProxy;

import autorizacion.ws.sri.gob.ec.Autorizacion;
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

	private static final Logger logger = LogManager.getLogger(SriBO.class);

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
		List<Invoice> invoices = invoiceRepository.findByAccessKeyAndIsDeleted(claveAcceso, false);
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
		String xmlAuthorization = "";
		Factura factura;
		Invoice invoice;
		try {
			proxy = new AutorizacionComprobanteProxy(wsdlAutorizacion);
		} catch (MalformedURLException e) {
			logger.error("autorizarComprobante", e);
			throw new ResourceNotFoundException(String.format("El archivo WSDL en la ruta %s no está disponible", wsdlAutorizacion));
		}
		RespuestaComprobante respuestaComprobante = proxy.autorizacionIndividual(claveAcceso);
		if (respuestaComprobante == null || respuestaComprobante.getAutorizaciones() == null || respuestaComprobante.getAutorizaciones().getAutorizacion() == null || respuestaComprobante.getAutorizaciones().getAutorizacion().isEmpty()) {
			throw new ResourceNotFoundException(String.format("No se puede autorizar la factura con clave de acceso %s", claveAcceso));
		}
		Autorizacion autorizacion = respuestaComprobante.getAutorizaciones().getAutorizacion().get(0);
		Timestamp timestamp = new Timestamp(autorizacion.getFechaAutorizacion().toGregorianCalendar().getTimeInMillis());
		try {
			factura = JaxbUtils.unmarshall(autorizacion.getComprobante(), Factura.class);
		} catch (Exception e) {
			logger.error("autorizarComprobante", e);
			throw new ResourceNotFoundException(String.format("No se puede procesar la factura %s", autorizacion.getComprobante()));
		}
		try {
			xmlAuthorization = JaxbUtils.marshallAsString(respuestaComprobante);
		} catch (Exception e) {
			logger.error("autorizarComprobante", e);
			throw new VeronicaException(e.getMessage());
		}
		List<Invoice> invoices = invoiceRepository.findByAccessKeyAndIsDeleted(claveAcceso, false);
		if (invoices == null || invoices.isEmpty()) {
			invoice = new Invoice();
			invoice.setAccessKey(claveAcceso);
			invoice.setSupplierId(factura.getInfoTributaria().getRuc());
			invoice.setCustomerId(factura.getInfoFactura().getIdentificacionComprador());
			invoice.setInvoiceNumber(factura.getInfoTributaria().getSecuencial());
			invoice.setIssueDate(DateUtils.getFechaFromStringddMMyyyy(factura.getInfoFactura().getFechaEmision()));
			invoice.setSriVersion(factura.getVersion());
			invoice.setXmlContent(autorizacion.getComprobante());
		} else {
			invoice = invoices.get(0);
		}
		invoice.setXmlAuthorization(xmlAuthorization);
		invoice.setInternalStatusId(autorizacion.getEstado().compareTo(SRI_APPLIED) == 0 ? APPLIED : INVALID);
		invoice.setAuthorizationDate(timestamp);
		invoiceRepository.save(invoice);
		RespuestaComprobanteDTO respuestaComprobanteDTO = respuestaComprobanteMapper.convert(respuestaComprobante);
		return respuestaComprobanteDTO;
	}

}