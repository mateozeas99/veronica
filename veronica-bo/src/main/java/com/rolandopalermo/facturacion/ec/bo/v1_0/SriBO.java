package com.rolandopalermo.facturacion.ec.bo.v1_0;

import static com.rolandopalermo.facturacion.ec.common.util.Constantes.POSTED;
import static com.rolandopalermo.facturacion.ec.common.util.Constantes.REJECTED;
import static com.rolandopalermo.facturacion.ec.common.util.Constantes.SRI_REJECTED;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.common.exception.ResourceNotFoundException;
import com.rolandopalermo.facturacion.ec.dto.v1_0.sri.RespuestaSolicitudDTO;
import com.rolandopalermo.facturacion.ec.mapper.RespuestaSolicitudMapper;
import com.rolandopalermo.facturacion.ec.persistence.entity.Invoice;
import com.rolandopalermo.facturacion.ec.persistence.repository.InvoiceRepository;
import com.rolandopalermo.facturacion.ec.soap.client.EnvioComprobantesProxy;

@Service
public class SriBO {

	@Value("${sri.wsdl.recepcion}")
	private String wsdlRecepcion;

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
			throw new ResourceNotFoundException(String.format("El WSDL %s no está disponible", wsdlRecepcion));
		}
		List<Invoice> invoices = invoiceRepository.findByAccessKey(claveAcceso);
		if (invoices == null || invoices.isEmpty()) {
			throw new ResourceNotFoundException(String.format("No se pudo encontrar la factura con clave de acceso %s", claveAcceso));
		}
		Invoice invoice = invoices.get(0);
		RespuestaSolicitudDTO respuestaSolicitudDTO = respuestaSolicitudMapper.toModel(proxy.enviarComprobante(invoice.getXmlContent().getBytes()));
		if (respuestaSolicitudDTO.getEstado().compareTo(SRI_REJECTED) == 0) {
			invoice.setInternalStatusId(REJECTED);
		} else {
			invoice.setInternalStatusId(POSTED);
		}
		invoiceRepository.save(invoice);
		return respuestaSolicitudDTO;
	}

}