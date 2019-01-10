package com.rolandopalermo.facturacion.ec.bo.v1_0;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.common.exception.ResourceNotFoundException;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.util.DateUtils;
import com.rolandopalermo.facturacion.ec.persistence.entity.Invoice;
import com.rolandopalermo.facturacion.ec.persistence.repository.InvoiceRepository;
import com.rolandopalermo.facturacion.ec.ride.RIDEGenerator;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service("rideBO")
public class RideBO {

	@Value("${sri.wsdl.autorizacion}")
	private String wsdlAutorizacion;

	@Autowired
	private InvoiceRepository invoiceRepository;
	
	private static final Logger logger = LogManager.getLogger(RideBO.class);

	@Autowired
	private SriBO sriBO;

	public byte[] generateRIDE(String claveAcceso) throws ResourceNotFoundException, VeronicaException {
		List<Invoice> invoices = invoiceRepository.findByAccessKeyAndIsDeleted(claveAcceso, false);
		if (invoices == null || invoices.isEmpty()) {
			sriBO.autorizarComprobante(claveAcceso);
			invoices = invoiceRepository.findByAccessKeyAndIsDeleted(claveAcceso, false);
			if (invoices == null || invoices.isEmpty()) {
				throw new ResourceNotFoundException(String.format("No se pudo encontrar la factura con clave de acceso %s", claveAcceso));
			}
		}
		final Invoice invoice = invoices.get(0);
		File comprobante;
		try {
			comprobante = File.createTempFile(claveAcceso, ".xml");
			Path path = Paths.get(comprobante.getAbsolutePath());
			try (BufferedWriter writer = Files.newBufferedWriter(path)) {
				writer.write(invoice.getXmlContent());
			}
		} catch (IOException e) {
			logger.error("generateRIDE", e);
			throw new VeronicaException("Ocurrió un error interno al generar el PDF");
		}
		if (invoice.getInternalStatusId() == 3) {
			try {
				JasperPrint jasperPrint = RIDEGenerator.convertirFacturaARide(claveAcceso, DateUtils.convertirTimestampToDate(invoice.getAuthorizationDate()), comprobante.getAbsolutePath());
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
			throw new ResourceNotFoundException(String.format("No se pudo autorizar la factura con clave de acceso %s", claveAcceso));
		}
	}

}