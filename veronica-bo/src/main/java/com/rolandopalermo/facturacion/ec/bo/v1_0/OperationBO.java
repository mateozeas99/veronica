package com.rolandopalermo.facturacion.ec.bo.v1_0;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.dto.v1_0.CertificadoDigitalDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.MetodoPagoDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.TipoDocumentoRetenidoDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.TipoImpuestoDTO;
import com.rolandopalermo.facturacion.ec.persistence.entity.DigitalCert;
import com.rolandopalermo.facturacion.ec.persistence.entity.PaymentMethod;
import com.rolandopalermo.facturacion.ec.persistence.entity.TaxType;
import com.rolandopalermo.facturacion.ec.persistence.entity.ReceiptType;
import com.rolandopalermo.facturacion.ec.persistence.repository.DigitalCertRepository;
import com.rolandopalermo.facturacion.ec.persistence.repository.PaymentMethodRepository;
import com.rolandopalermo.facturacion.ec.persistence.repository.TaxTypeRepository;
import com.rolandopalermo.facturacion.ec.persistence.repository.WithheldReceiptTypeRepository;

@Service("operationBO")
public class OperationBO {

	@Autowired
	private DigitalCertRepository digitalCertRepository;

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Autowired
	private WithheldReceiptTypeRepository receiptTypeRepository;

	@Autowired
	private TaxTypeRepository taxTypeRepository;

	public void saveDigitalCert(CertificadoDigitalDTO certificadoDigital) {
		DigitalCert cert = new DigitalCert();
		cert.setActive(true);
		cert.setInsertDate(new Date());
		cert.setDigitalCert(certificadoDigital.getArchivo());
		cert.setOwner(certificadoDigital.getRucPropietario());
		cert.setPassword(certificadoDigital.getPassword());
		digitalCertRepository.save(cert);
	}

	public void savePaymentMethod(MetodoPagoDTO metodoPagoDTO) {
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setCode(metodoPagoDTO.getCodigo());
		paymentMethod.setDescription(metodoPagoDTO.getDescripcion());
		paymentMethodRepository.save(paymentMethod);
	}

	public void saveReceiptType(TipoDocumentoRetenidoDTO tipoDocumentoRetenidoDTO) {
		ReceiptType receiptType = new ReceiptType();
		receiptType.setCode(tipoDocumentoRetenidoDTO.getCodigo());
		receiptType.setDescription(tipoDocumentoRetenidoDTO.getDescripcion());
		receiptTypeRepository.save(receiptType);
	}

	public void saveTaxType(TipoImpuestoDTO tipoImpuestoDTO) {
		TaxType taxType = new TaxType();
		taxType.setCode(tipoImpuestoDTO.getCodigo());
		taxType.setDescription(tipoImpuestoDTO.getDescripcion());
		taxTypeRepository.save(taxType);
	}

}