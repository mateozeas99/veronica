package com.rolandopalermo.facturacion.ec.service.v1_0.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.dto.v1_0.CertificadoDigitalDTO;
import com.rolandopalermo.facturacion.ec.persistence.entity.DigitalCert;

@Service("digitalCertServiceImpl")
public class DigitalCertServiceImpl extends GenericCRUDServiceImpl<DigitalCert, CertificadoDigitalDTO> {

	@Override
	public DigitalCert mapTo(CertificadoDigitalDTO certificadoDigital) {
		DigitalCert cert = new DigitalCert();
		cert.setActive(true);
		cert.setInsertDate(new Date());
		cert.setDigitalCert(certificadoDigital.getArchivo());
		cert.setOwner(certificadoDigital.getRucPropietario());
		cert.setPassword(certificadoDigital.getPassword());
		return cert;
	}

	@Override
	public CertificadoDigitalDTO build(DigitalCert domainObject) {
		return null;
	}

}