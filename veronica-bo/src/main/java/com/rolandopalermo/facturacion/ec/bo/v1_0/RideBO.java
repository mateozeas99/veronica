package com.rolandopalermo.facturacion.ec.bo.v1_0;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.common.exception.ResourceNotFoundException;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.util.DateUtils;
import com.rolandopalermo.facturacion.ec.common.util.DocumentType;
import com.rolandopalermo.facturacion.ec.persistence.entity.Bol;
import com.rolandopalermo.facturacion.ec.persistence.entity.Invoice;
import com.rolandopalermo.facturacion.ec.persistence.entity.Withholding;
import com.rolandopalermo.facturacion.ec.persistence.repository.BolRepository;
import com.rolandopalermo.facturacion.ec.persistence.repository.InvoiceRepository;
import com.rolandopalermo.facturacion.ec.persistence.repository.WithholdingRepository;
import com.rolandopalermo.facturacion.ec.ride.RIDEGenerator;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service("rideBO")
public class RideBO {

	@Value("${sri.wsdl.autorizacion}")
	private String wsdlAutorizacion;

	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	private WithholdingRepository withHoldingRepository;
	
	@Autowired
	private BolRepository bolRepository;
	
	@Autowired
	private RIDEGenerator rideGenerator;
	
	private static final Logger logger = LogManager.getLogger(RideBO.class);

	@Autowired
	private SriBO sriBO;

	public byte[] generateInvoiceRIDE(String accessKey) throws ResourceNotFoundException, VeronicaException {
		List<Invoice> invoices = invoiceRepository.findByAccessKeyAndIsDeleted(accessKey, false);
		if (invoices == null || invoices.isEmpty()) {
			sriBO.applyInvoice(accessKey);
			invoices = invoiceRepository.findByAccessKeyAndIsDeleted(accessKey, false);
			if (invoices == null || invoices.isEmpty()) {
				throw new ResourceNotFoundException(
						String.format("No se pudo encontrar la factura con clave de acceso %s", accessKey));
			}
		}
		final Invoice invoice = invoices.get(0);
		return generatePDF(
				accessKey, 
				invoice.getXmlContent(), 
				invoice.getInternalStatusId(),
				invoice.getAuthorizationDate(),
				DocumentType.FACTURA);
	}
	
	public byte[] generateWithHoldingRIDE(String accessKey) throws ResourceNotFoundException, VeronicaException {
		List<Withholding> withholdings = withHoldingRepository.findByAccessKeyAndIsDeleted(accessKey, false);
		if (withholdings == null || withholdings.isEmpty()) {
			sriBO.applyWithHolding(accessKey);
			withholdings = withHoldingRepository.findByAccessKeyAndIsDeleted(accessKey, false);
			if (withholdings == null || withholdings.isEmpty()) {
				throw new ResourceNotFoundException(
						String.format("No se pudo encontrar el comprobante de retención con clave de acceso %s", accessKey));
			}
		}
		final Withholding withholding = withholdings.get(0);
		return generatePDF(
				accessKey, 
				withholding.getXmlContent(), 
				withholding.getInternalStatusId(),
				withholding.getAuthorizationDate(),
				DocumentType.COMPROBANTE_RETENCION);
	}
	
	public byte[] generateBolRIDE(String accessKey) throws ResourceNotFoundException, VeronicaException {
		List<Bol> bols = bolRepository.findByAccessKeyAndIsDeleted(accessKey, false);
		if (bols == null || bols.isEmpty()) {
			sriBO.applyBillOFLanding(accessKey);
			bols = bolRepository.findByAccessKeyAndIsDeleted(accessKey, false);
			if (bols == null || bols.isEmpty()) {
				throw new ResourceNotFoundException(
						String.format("No se pudo encontrar la guía de remisión con clave de acceso %s", accessKey));
			}
		}
		final Bol bol = bols.get(0);
		return generatePDF(
				accessKey, 
				bol.getXmlContent(), 
				bol.getInternalStatusId(),
				bol.getAuthorizationDate(),
				DocumentType.GUITA_REMISION);
	}
	
	private byte[] generatePDF(String accessKey, String xmlContent, long internalStatusId, Timestamp authorizationDate, DocumentType documentType) throws ResourceNotFoundException, VeronicaException {
		File comprobante;
		try {
			comprobante = File.createTempFile(accessKey, ".xml");
			Path path = Paths.get(comprobante.getAbsolutePath());
			try (BufferedWriter writer = Files.newBufferedWriter(path)) {
				writer.write(xmlContent);
			}
		} catch (IOException e) {
			logger.error("generateRIDE", e);
			throw new VeronicaException("Ocurrió un error interno al generar el PDF");
		}
		if (internalStatusId == 3) {
			try {
				JasperPrint jasperPrint = null;
				switch (documentType) {
					case FACTURA:
						jasperPrint = rideGenerator.convertirFacturaARide(accessKey, DateUtils.convertirTimestampToDate(authorizationDate), comprobante.getAbsolutePath());
						break;
					case COMPROBANTE_RETENCION:
						jasperPrint = rideGenerator.convertirRetencionARide(accessKey, DateUtils.convertirTimestampToDate(authorizationDate), comprobante.getAbsolutePath());
						break;
					case GUITA_REMISION:
						jasperPrint = rideGenerator.convertirGuiaRemisionARide(accessKey, DateUtils.convertirTimestampToDate(authorizationDate), comprobante.getAbsolutePath());
						break;
				}
				if (jasperPrint == null) {
					new VeronicaException(String.format("No se pudo generar el PDF para el comprobante con clave de acceso %s", accessKey));
				}
				if (!comprobante.delete()) {
					new VeronicaException(String.format("No se puede eliminar el archivo temporal en %s", comprobante.getAbsolutePath()));
				}
				return JasperExportManager.exportReportToPdf(jasperPrint);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("generateRIDE", e);
				throw new VeronicaException("No se pudo generar el archivo PDF");
			}
		} else {
			throw new ResourceNotFoundException(String.format("No se pudo autorizar el comprobante con clave de acceso %s", accessKey));
		}
	}

}