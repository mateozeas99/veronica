package com.rolandopalermo.facturacion.ec.bo.v1_0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("rideBO")
public class RideBO {

	@Value("${sri.wsdl.autorizacion}")
	private String wsdlAutorizacion;
	
	@Autowired
	private SriBO sriBO;
	
//	public void generateRIDE(String claveAcceso) {
//		byte[] pdfContent = new byte[0];
//		RespuestaComprobanteDTO respuesta = sriBO.autorizarComprobante(claveAcceso, wsdlAutorizacion);
//	}
	
}