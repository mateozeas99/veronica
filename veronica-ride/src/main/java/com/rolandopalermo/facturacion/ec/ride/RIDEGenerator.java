package com.rolandopalermo.facturacion.ec.ride;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.persistence.entity.PaymentMethod;
import com.rolandopalermo.facturacion.ec.persistence.entity.TaxType;
import com.rolandopalermo.facturacion.ec.persistence.entity.ReceiptType;
import com.rolandopalermo.facturacion.ec.persistence.repository.PaymentMethodRepository;
import com.rolandopalermo.facturacion.ec.persistence.repository.TaxTypeRepository;
import com.rolandopalermo.facturacion.ec.persistence.repository.WithheldReceiptTypeRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
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
	
	@PostConstruct
	public void init() {
		List<PaymentMethod> lstPaymentMethods = paymentMethodRepository.findAll();
		List<ReceiptType> lstReceiptTypes = receiptTypeRepository.findAll();
		List<TaxType> lstTaxTypes = taxTypeRepository.findAll();
		hmapFormasPago = (HashMap<String, String>) lstPaymentMethods
				.stream()
				.collect(Collectors.toMap(PaymentMethod::getCode, PaymentMethod::getDescription));
		hmapTiposDocumentos = (HashMap<String, String>) lstReceiptTypes
				.stream()
				.collect(Collectors.toMap(ReceiptType::getCode, ReceiptType::getDescription));
		hmapTiposImpuestos = (HashMap<String, String>) lstTaxTypes
				.stream()
				.collect(Collectors.toMap(TaxType::getCode, TaxType::getDescription));
	}
	
	public JasperPrint convertirFacturaARide(String numeroAutorizacion, String fechaAutorizacion, String xmlFilePath) throws JRException {
		InputStream employeeReportStream = RIDEGenerator.class.getResourceAsStream("/com/rolandopalermo/facturacion/ec/ride/RIDE_factura.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("numeroAutorizacion", numeroAutorizacion);
		parameters.put("fechaAutorizacion", fechaAutorizacion);
		parameters.put("hmapFormasPago", hmapFormasPago);
		JRXmlDataSource xmlDataSource = new JRXmlDataSource(xmlFilePath, "/factura");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, xmlDataSource);
		return jasperPrint;
	}
	
	public JasperPrint convertirRetencionARide(String numeroAutorizacion, String fechaAutorizacion, String xmlFilePath) throws JRException {
		InputStream employeeReportStream = RIDEGenerator.class.getResourceAsStream("/com/rolandopalermo/facturacion/ec/ride/RIDE_retencion.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("numeroAutorizacion", numeroAutorizacion);
		parameters.put("fechaAutorizacion", fechaAutorizacion);
		parameters.put("hmapTiposDocumentos", hmapTiposDocumentos);
		parameters.put("hmapTiposImpuestos", hmapTiposImpuestos);
		JRXmlDataSource xmlDataSource = new JRXmlDataSource(xmlFilePath, "/comprobanteRetencion");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, xmlDataSource);
		return jasperPrint;
	}
	
	public JasperPrint convertirGuiaRemisionARide(String numeroAutorizacion, String fechaAutorizacion, String xmlFilePath) throws JRException {
		InputStream employeeReportStream = RIDEGenerator.class.getResourceAsStream("/com/rolandopalermo/facturacion/ec/ride/RIDE_guia_remision.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(employeeReportStream);
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("numeroAutorizacion", numeroAutorizacion);
		parameters.put("fechaAutorizacion", fechaAutorizacion);
		parameters.put("hmapTiposDocumentos", hmapTiposDocumentos);
		JRXmlDataSource xmlDataSource = new JRXmlDataSource(xmlFilePath, "/guiaRemision");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, xmlDataSource);
		return jasperPrint;
	}

}