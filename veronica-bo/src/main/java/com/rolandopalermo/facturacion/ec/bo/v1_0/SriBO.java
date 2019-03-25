package com.rolandopalermo.facturacion.ec.bo.v1_0;

import static com.rolandopalermo.facturacion.ec.common.util.Constants.APPLIED;
import static com.rolandopalermo.facturacion.ec.common.util.Constants.INVALID;
import static com.rolandopalermo.facturacion.ec.common.util.Constants.POSTED;
import static com.rolandopalermo.facturacion.ec.common.util.Constants.REJECTED;
import static com.rolandopalermo.facturacion.ec.common.util.Constants.SRI_APPLIED;
import static com.rolandopalermo.facturacion.ec.common.util.Constants.SRI_REJECTED;

import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.common.exception.ResourceNotFoundException;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.util.JaxbUtils;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.AutorizacionDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaComprobanteDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaSolicitudDTO;
import com.rolandopalermo.facturacion.ec.mapper.sri.RespuestaComprobanteMapper;
import com.rolandopalermo.facturacion.ec.mapper.sri.RespuestaSolicitudMapper;
import com.rolandopalermo.facturacion.ec.modelo.factura.Factura;
import com.rolandopalermo.facturacion.ec.modelo.guia.GuiaRemision;
import com.rolandopalermo.facturacion.ec.modelo.retencion.ComprobanteRetencion;
import com.rolandopalermo.facturacion.ec.persistence.entity.Bol;
import com.rolandopalermo.facturacion.ec.persistence.entity.Invoice;
import com.rolandopalermo.facturacion.ec.persistence.entity.Withholding;
import com.rolandopalermo.facturacion.ec.persistence.repository.BolRepository;
import com.rolandopalermo.facturacion.ec.persistence.repository.InvoiceRepository;
import com.rolandopalermo.facturacion.ec.persistence.repository.WithholdingRepository;
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
	
	@Autowired
	private WithholdingRepository withHoldingRepository;
	
	@Autowired
	private BolRepository bolRepository;
	
	@Autowired
	private BolBO bolBO;
	
	@Autowired
	private InvoiceBO invoiceBO;
	
	@Autowired
	private WithHoldingBO withHoldingBO;

	private static final Logger logger = LogManager.getLogger(SriBO.class);

	@Autowired
	private RespuestaSolicitudMapper respuestaSolicitudMapper;

	public RespuestaSolicitudDTO postInvoice(String claveAcceso) throws ResourceNotFoundException {
		List<Invoice> invoices = invoiceRepository.findByAccessKeyAndIsDeleted(claveAcceso, false);
		if (invoices == null || invoices.isEmpty()) {
			throw new ResourceNotFoundException(
					String.format("No se pudo encontrar la factura con clave de acceso %s", claveAcceso));
		}
		Invoice invoice = invoices.get(0);
		byte[] xmlContent = invoice.getXmlContent().getBytes();
		if (xmlContent == null) {
			throw new ResourceNotFoundException(
					String.format("El contenido de la factura con clave de acceso %s es nulo", claveAcceso));
		}
		RespuestaSolicitudDTO respuestaSolicitudDTO = post(xmlContent);
		if (respuestaSolicitudDTO.getEstado().compareTo(SRI_REJECTED) == 0) {
			invoice.setInternalStatusId(REJECTED);
		} else {
			invoice.setInternalStatusId(POSTED);
		}
		invoiceRepository.save(invoice);
		return respuestaSolicitudDTO;
	}
	
	public RespuestaSolicitudDTO postWithHolding(String claveAcceso) throws ResourceNotFoundException {
		List<Withholding> withHoldings = withHoldingRepository.findByAccessKeyAndIsDeleted(claveAcceso, false);
		if (withHoldings == null || withHoldings.isEmpty()) {
			throw new ResourceNotFoundException(
					String.format("No se pudo encontrar el comprobante de retención con clave de acceso %s", claveAcceso));
		}
		Withholding withHolding = withHoldings.get(0);
		byte[] xmlContent = withHolding.getXmlContent().getBytes();
		if (xmlContent == null) {
			throw new ResourceNotFoundException(
					String.format("El contenido del comprobante de retención con clave de acceso %s es nulo", claveAcceso));
		}
		RespuestaSolicitudDTO respuestaSolicitudDTO = post(xmlContent);
		if (respuestaSolicitudDTO.getEstado().compareTo(SRI_REJECTED) == 0) {
			withHolding.setInternalStatusId(REJECTED);
		} else {
			withHolding.setInternalStatusId(POSTED);
		}
		withHoldingRepository.save(withHolding);
		return respuestaSolicitudDTO;
	}
	
	public RespuestaSolicitudDTO postBillOFLanding(String claveAcceso) throws ResourceNotFoundException {
		List<Bol> bols = bolRepository.findByAccessKeyAndIsDeleted(claveAcceso, false);
		if (bols == null || bols.isEmpty()) {
			throw new ResourceNotFoundException(
					String.format("No se pudo encontrar la guia de remisión electrónica con clave de acceso %s", claveAcceso));
		}
		Bol bol = bols.get(0);
		byte[] xmlContent = bol.getXmlContent().getBytes();
		if (xmlContent == null) {
			throw new ResourceNotFoundException(
					String.format("El contenido de la guia de remisión electrónica con clave de acceso %s es nulo", claveAcceso));
		}
		RespuestaSolicitudDTO respuestaSolicitudDTO = post(xmlContent);
		if (respuestaSolicitudDTO.getEstado().compareTo(SRI_REJECTED) == 0) {
			bol.setInternalStatusId(REJECTED);
		} else {
			bol.setInternalStatusId(POSTED);
		}
		bolRepository.save(bol);
		return respuestaSolicitudDTO;
	}

	public RespuestaComprobanteDTO applyInvoice(String claveAcceso) throws ResourceNotFoundException, VeronicaException {
		Factura factura;
		Invoice invoice;
		RespuestaComprobanteDTO respuestaComprobanteDTO = apply(claveAcceso);
		AutorizacionDTO autorizacion = respuestaComprobanteDTO.getAutorizaciones().get(0);
		try {
			factura = JaxbUtils.unmarshall(autorizacion.getComprobante(), Factura.class);
		} catch (Exception e) {
			logger.error("applyInvoice", e);
			throw new ResourceNotFoundException(
					String.format("No se puede procesar la factura %s", autorizacion.getComprobante()));
		}
		Timestamp timestamp = new Timestamp(respuestaComprobanteDTO.getTimestamp());
		List<Invoice> invoices = invoiceRepository.findByAccessKeyAndIsDeleted(claveAcceso, false);
		if (invoices == null || invoices.isEmpty()) {
			invoice = invoiceBO.toEntity(factura, autorizacion.getComprobante());
		} else {
			invoice = invoices.get(0);
		}
		invoice.setXmlAuthorization(respuestaComprobanteDTO.getContentAsXML());
		invoice.setInternalStatusId(autorizacion.getEstado().compareTo(SRI_APPLIED) == 0 ? APPLIED : INVALID);
		invoice.setAuthorizationDate(timestamp);
		invoiceRepository.save(invoice);
		return respuestaComprobanteDTO;
	}
	
	public RespuestaComprobanteDTO applyWithHolding(String claveAcceso) throws ResourceNotFoundException, VeronicaException {
		ComprobanteRetencion comprobanteRetencion;
		Withholding withHolding;
		RespuestaComprobanteDTO respuestaComprobanteDTO = apply(claveAcceso);
		AutorizacionDTO autorizacion = respuestaComprobanteDTO.getAutorizaciones().get(0);
		try {
			comprobanteRetencion = JaxbUtils.unmarshall(autorizacion.getComprobante(), ComprobanteRetencion.class);
		} catch (Exception e) {
			logger.error("applyWithHolding", e);
			throw new ResourceNotFoundException(
					String.format("No se puede procesar el comprobante de retención %s", autorizacion.getComprobante()));
		}
		Timestamp timestamp = new Timestamp(respuestaComprobanteDTO.getTimestamp());
		List<Withholding> withHoldings = withHoldingRepository.findByAccessKeyAndIsDeleted(claveAcceso, false);
		if (withHoldings == null || withHoldings.isEmpty()) {
			withHolding = withHoldingBO.toEntity(comprobanteRetencion, autorizacion.getComprobante());
		} else {
			withHolding = withHoldings.get(0);
		}
		withHolding.setXmlAuthorization(respuestaComprobanteDTO.getContentAsXML());
		withHolding.setInternalStatusId(autorizacion.getEstado().compareTo(SRI_APPLIED) == 0 ? APPLIED : INVALID);
		withHolding.setAuthorizationDate(timestamp);
		withHoldingRepository.save(withHolding);
		return respuestaComprobanteDTO;
	}
	
	public RespuestaComprobanteDTO applyBillOFLanding(String claveAcceso) throws ResourceNotFoundException, VeronicaException {
		GuiaRemision guiaRemision;
		Bol bol;
		RespuestaComprobanteDTO respuestaComprobanteDTO = apply(claveAcceso);
		AutorizacionDTO autorizacion = respuestaComprobanteDTO.getAutorizaciones().get(0);
		try {
			guiaRemision = JaxbUtils.unmarshall(autorizacion.getComprobante(), GuiaRemision.class);
		} catch (Exception e) {
			logger.error("applyBillOFLanding", e);
			throw new ResourceNotFoundException(
					String.format("No se puede procesar la guía de remisión %s", autorizacion.getComprobante()));
		}
		Timestamp timestamp = new Timestamp(respuestaComprobanteDTO.getTimestamp());
		List<Bol> bols = bolRepository.findByAccessKeyAndIsDeleted(claveAcceso, false);
		if (bols == null || bols.isEmpty()) {
			bol = bolBO.toEntity(guiaRemision, autorizacion.getComprobante());
		} else {
			bol = bols.get(0);
		}
		bol.setXmlAuthorization(respuestaComprobanteDTO.getContentAsXML());
		bol.setInternalStatusId(autorizacion.getEstado().compareTo(SRI_APPLIED) == 0 ? APPLIED : INVALID);
		bol.setAuthorizationDate(timestamp);
		bolRepository.save(bol);
		return respuestaComprobanteDTO;
	}
	
	private RespuestaComprobanteDTO apply(String claveAcceso) throws ResourceNotFoundException, VeronicaException {
		AutorizacionComprobanteProxy proxy;
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
		RespuestaComprobanteDTO respuestaComprobanteDTO = respuestaComprobanteMapper.convert(respuestaComprobante);
		return respuestaComprobanteDTO;
	}
	
	private RespuestaSolicitudDTO post(byte[] xmlContent) {
		EnvioComprobantesProxy proxy;
		RespuestaSolicitudDTO respuestaSolicitudDTO = null;
		try {
			proxy = new EnvioComprobantesProxy(wsdlRecepcion);
			respuestaSolicitudDTO = respuestaSolicitudMapper.convert(proxy.enviarComprobante(xmlContent));
		} catch (MalformedURLException e) {
			logger.error("enviarComprobante", e);
			throw new ResourceNotFoundException(
					String.format("El archivo WSDL en la ruta %s no está disponible", wsdlRecepcion));
		}
		return respuestaSolicitudDTO;
	}

}