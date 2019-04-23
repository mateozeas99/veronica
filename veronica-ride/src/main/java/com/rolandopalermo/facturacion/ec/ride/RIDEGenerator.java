package com.rolandopalermo.facturacion.ec.ride;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.util.XmlUtils;
import com.rolandopalermo.facturacion.ec.persistence.entity.PaymentMethod;
import com.rolandopalermo.facturacion.ec.persistence.entity.ReceiptType;
import com.rolandopalermo.facturacion.ec.persistence.entity.TaxType;
import com.rolandopalermo.facturacion.ec.persistence.repository.PaymentMethodRepository;
import com.rolandopalermo.facturacion.ec.persistence.repository.TaxTypeRepository;
import com.rolandopalermo.facturacion.ec.persistence.repository.WithheldReceiptTypeRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

@Service("rideGenerator")
public class RIDEGenerator {

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Autowired
	private WithheldReceiptTypeRepository receiptTypeRepository;

	@Autowired
	private TaxTypeRepository taxTypeRepository;

	private HashMap<String, String> hmapFormasPago;
	private HashMap<String, String> hmapTiposDocumentos;
	private HashMap<String, String> hmapTiposImpuestos;

	private static final Logger logger = LogManager.getLogger(RIDEGenerator.class);

	@PostConstruct
	public void init() {
		List<PaymentMethod> lstPaymentMethods = paymentMethodRepository.findAll();
		List<ReceiptType> lstReceiptTypes = receiptTypeRepository.findAll();
		List<TaxType> lstTaxTypes = taxTypeRepository.findAll();
		hmapFormasPago = (HashMap<String, String>) lstPaymentMethods.stream()
				.collect(Collectors.toMap(PaymentMethod::getCode, PaymentMethod::getDescription));
		hmapTiposDocumentos = (HashMap<String, String>) lstReceiptTypes.stream()
				.collect(Collectors.toMap(ReceiptType::getCode, ReceiptType::getDescription));
		hmapTiposImpuestos = (HashMap<String, String>) lstTaxTypes.stream()
				.collect(Collectors.toMap(TaxType::getCode, TaxType::getDescription));
	}

	public byte[] buildPDF(String xmlContent, String numeroAutorizacion, String fechaAutorizacion)
			throws VeronicaException {
		File comprobante;
		try {
			comprobante = File.createTempFile(numeroAutorizacion, ".xml");
			Path path = Paths.get(comprobante.getAbsolutePath());
			try (BufferedWriter writer = Files.newBufferedWriter(path)) {
				writer.write(xmlContent);
			}
		} catch (IOException e) {
			logger.error("buildPDF", e);
			throw new VeronicaException("Ocurrió un error interno al generar el PDF");
		}
		try {
			String xmlRootElement = XmlUtils.getXmlRootElement(xmlContent);
			String rootElement = "/".concat(xmlRootElement);
			StringBuilder sbTemplate = new StringBuilder("/com/rolandopalermo/facturacion/ec/ride/RIDE_");
			sbTemplate.append(xmlRootElement);
			sbTemplate.append(".jrxml");
			String template = sbTemplate.toString();
			InputStream employeeReportStream = RIDEGenerator.class.getResourceAsStream(template);
			JasperReport jasperReport;
			jasperReport = JasperCompileManager.compileReport(employeeReportStream);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("numeroAutorizacion", numeroAutorizacion);
			parameters.put("fechaAutorizacion", fechaAutorizacion);
			parameters.put("hmapTiposDocumentos", hmapTiposDocumentos);
			parameters.put("hmapTiposImpuestos", hmapTiposImpuestos);
			parameters.put("hmapFormasPago", hmapFormasPago);
			JRXmlDataSource xmlDataSource = new JRXmlDataSource(comprobante.getAbsolutePath(), rootElement);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, xmlDataSource);
			if (jasperPrint == null) {
				new VeronicaException(String.format(
						"No se pudo generar el PDF para el comprobante con clave de acceso %s", numeroAutorizacion));
			}
			if (!comprobante.delete()) {
				new VeronicaException(
						String.format("No se puede eliminar el archivo temporal en %s", comprobante.getAbsolutePath()));
			}
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException e) {
			logger.error("buildPDF", e);
			throw new VeronicaException("Ocurrió un error interno al generar el PDF");
		}
	}

}