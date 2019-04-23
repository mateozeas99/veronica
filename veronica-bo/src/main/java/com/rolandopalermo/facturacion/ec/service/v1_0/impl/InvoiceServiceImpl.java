package com.rolandopalermo.facturacion.ec.service.v1_0.impl;

import static com.rolandopalermo.facturacion.ec.common.util.Constants.CREATED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.common.converter.JaxbConverter;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.util.DateUtils;
import com.rolandopalermo.facturacion.ec.dto.v1_0.invoice.FacturaDTO;
import com.rolandopalermo.facturacion.ec.modelo.factura.Factura;
import com.rolandopalermo.facturacion.ec.persistence.entity.Invoice;

@Service("invoiceServiceImpl")
public class InvoiceServiceImpl extends GenericSRIServiceImpl<FacturaDTO, Factura, Invoice> {

	@Override
	public Invoice toEntity(Factura factura, String contentAsXML) throws VeronicaException {
		Invoice invoice = new Invoice();
		invoice.setAccessKey(factura.getInfoTributaria().getClaveAcceso());
		invoice.setSriVersion(factura.getVersion());
		invoice.setXmlContent(contentAsXML);
		invoice.setSupplierId(factura.getInfoTributaria().getRuc());
		invoice.setCustomerId(factura.getInfoFactura().getIdentificacionComprador());
		invoice.setIssueDate(DateUtils.getFechaFromStringddMMyyyy(factura.getInfoFactura().getFechaEmision()));
		invoice.setInvoiceNumber(factura.getInfoTributaria().getSecuencial());
		invoice.setInternalStatusId(CREATED);
		return invoice;
	}

	@Override
	@Autowired
	@Qualifier("invoiceJaxbConverter")
	public void setJaxbConverter(JaxbConverter invoiceJaxbConverter) {
		jaxbConverter = invoiceJaxbConverter;
	}

}