package com.rolandopalermo.facturacion.ec.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.rolandopalermo.facturacion.ec.common.converter.JaxbConverter;

@Configuration
public class JaxbConfig {

	@Bean(name = "invoiceJaxbConverter")
	public JaxbConverter getInvoiceJaxbConverter() {
		JaxbConverter converter = new JaxbConverter();
		converter.setMarshaller(invoiceJaxbContextBean());
		converter.setUnmarshaller(invoiceJaxbContextBean());
		return converter;
	}
	
	@Bean(name = "bolJaxbConverter")
	public JaxbConverter getBolJaxbConverter() {
		JaxbConverter converter = new JaxbConverter();
		converter.setMarshaller(bolJaxbContextBean());
		converter.setUnmarshaller(bolJaxbContextBean());
		return converter;
	}
	
	@Bean(name = "cmJaxbConverter")
	public JaxbConverter getCmJaxbConverter() {
		JaxbConverter converter = new JaxbConverter();
		converter.setMarshaller(cmJaxbContextBean());
		converter.setUnmarshaller(cmJaxbContextBean());
		return converter;
	}
	
	@Bean(name = "dmJaxbConverter")
	public JaxbConverter getDmJaxbConverter() {
		JaxbConverter converter = new JaxbConverter();
		converter.setMarshaller(dmJaxbContextBean());
		converter.setUnmarshaller(dmJaxbContextBean());
		return converter;
	}
	
	@Bean(name = "withHoldingJaxbConverter")
	public JaxbConverter getWithHoldingJaxbConverter() {
		JaxbConverter converter = new JaxbConverter();
		converter.setMarshaller(withHoldingJaxbContextBean());
		converter.setUnmarshaller(withHoldingJaxbContextBean());
		return converter;
	}
	
	@Bean(name = "respuestaComprobanteJaxbConverter")
	public JaxbConverter getRespuestaComprobanteJaxbConverter() {
		JaxbConverter converter = new JaxbConverter();
		converter.setMarshaller(respuestaComprobanteJaxbContextBean());
		converter.setUnmarshaller(respuestaComprobanteJaxbContextBean());
		return converter;
	}

	@Bean
	public Jaxb2Marshaller respuestaComprobanteJaxbContextBean() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(new Class[] { autorizacion.ws.sri.gob.ec.RespuestaComprobante.class });
		return marshaller;
	}

	@Bean
	public Jaxb2Marshaller invoiceJaxbContextBean() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(new Class[] { com.rolandopalermo.facturacion.ec.modelo.factura.Factura.class });
		return marshaller;
	}
	
	@Bean
	public Jaxb2Marshaller bolJaxbContextBean() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(new Class[] { com.rolandopalermo.facturacion.ec.modelo.guia.GuiaRemision.class });
		return marshaller;
	}
	
	@Bean
	public Jaxb2Marshaller cmJaxbContextBean() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(new Class[] { com.rolandopalermo.facturacion.ec.modelo.notacredito.NotaCredito.class });
		return marshaller;
	}
	
	@Bean
	public Jaxb2Marshaller dmJaxbContextBean() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(new Class[] { com.rolandopalermo.facturacion.ec.modelo.notadebito.NotaDebito.class });
		return marshaller;
	}
	
	@Bean
	public Jaxb2Marshaller withHoldingJaxbContextBean() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(new Class[] { com.rolandopalermo.facturacion.ec.modelo.retencion.ComprobanteRetencion.class });
		return marshaller;
	}
	
}