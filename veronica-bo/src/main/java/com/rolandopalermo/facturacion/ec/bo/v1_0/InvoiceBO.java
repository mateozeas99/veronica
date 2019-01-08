package com.rolandopalermo.facturacion.ec.bo.v1_0;

import static com.rolandopalermo.facturacion.ec.common.util.Constantes.CREATED;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.util.DateUtils;
import com.rolandopalermo.facturacion.ec.common.util.FileUtils;
import com.rolandopalermo.facturacion.ec.common.util.SignerUtils;
import com.rolandopalermo.facturacion.ec.dto.v1_0.invoice.FacturaDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.invoice.FacturaIdDTO;
import com.rolandopalermo.facturacion.ec.mapper.FacturaMapper;
import com.rolandopalermo.facturacion.ec.modelo.factura.Factura;
import com.rolandopalermo.facturacion.ec.persistence.entity.DigitalCert;
import com.rolandopalermo.facturacion.ec.persistence.entity.Invoice;
import com.rolandopalermo.facturacion.ec.persistence.repository.DigitalCertRepository;
import com.rolandopalermo.facturacion.ec.persistence.repository.InvoiceRepository;

@Service
public class InvoiceBO {

	@Autowired
	private FacturaMapper facturaMapper;

	@Autowired
	private DigitalCertRepository digitalCertRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	public FacturaIdDTO createInvoice(FacturaDTO facturaDTO) throws VeronicaException {
		Factura factura = facturaMapper.toModel(facturaDTO);
		byte[] xmlContent;
		try {
			xmlContent = FileUtils.convertirObjAXML(factura);
			String rucNumber = factura.getInfoTributaria().getRuc();
			List<DigitalCert> certificados = digitalCertRepository.findByOwner(rucNumber);
			if (certificados == null || certificados.isEmpty()) {
				throw new Exception(String.format("No existe un certificado digital asociado al RUC %S", rucNumber));
			}
			byte[] signedXMLContent = SignerUtils.signXML(xmlContent, certificados.get(0).getDigitalCert(), certificados.get(0).getPassword());
			Invoice invoice = new Invoice();
			invoice.setAccessKey(factura.getInfoTributaria().getClaveAcceso());
			invoice.setSriVersion(factura.getVersion());
			invoice.setXmlContent(new String(signedXMLContent));
			invoice.setSupplierId(factura.getInfoTributaria().getRuc());
			invoice.setCustomerId(factura.getInfoFactura().getIdentificacionComprador());
			invoice.setIssueDate(DateUtils.getFechaFromStringddMMyyyy(factura.getInfoFactura().getFechaEmision()));
			invoice.setInvoiceNumber(factura.getInfoTributaria().getSecuencial());
			invoice.setInternalStatusId(CREATED);
			invoiceRepository.save(invoice);
			FacturaIdDTO facturaIdDTO = new FacturaIdDTO();
			facturaIdDTO.setClaveAcceso(invoice.getAccessKey());
			facturaIdDTO.setNumeroFactura(invoice.getInvoiceNumber());
			return facturaIdDTO;
		} catch (Exception e) {
			throw new VeronicaException(e.getMessage());
		}
		
	}

}