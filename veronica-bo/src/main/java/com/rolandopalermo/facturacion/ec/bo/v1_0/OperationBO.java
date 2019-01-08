package com.rolandopalermo.facturacion.ec.bo.v1_0;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.dto.v1_0.CertificadoDigitalDTO;
import com.rolandopalermo.facturacion.ec.persistence.entity.DigitalCert;
import com.rolandopalermo.facturacion.ec.persistence.repository.DigitalCertRepository;

@Service
public class OperationBO {

	@Autowired
	private DigitalCertRepository digitalCertRepository;
	
	public void saveDigitalCert(CertificadoDigitalDTO certificadoDigital) {
		DigitalCert cert = new DigitalCert();
		cert.setActive(true);
		cert.setInsertDate(new Date());
		cert.setDigitalCert(certificadoDigital.getArchivo());
		cert.setOwner(certificadoDigital.getRucPropietario());
		cert.setPassword(certificadoDigital.getPassword());
		digitalCertRepository.save(cert);
	}
	
}