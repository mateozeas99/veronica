package com.rolandopalermo.facturacion.ec.service.v1_0.impl;

import static com.rolandopalermo.facturacion.ec.common.util.Constants.CREATED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.common.converter.JaxbConverter;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.util.DateUtils;
import com.rolandopalermo.facturacion.ec.dto.v1_0.cm.NotaCreditoDTO;
import com.rolandopalermo.facturacion.ec.modelo.notacredito.NotaCredito;
import com.rolandopalermo.facturacion.ec.persistence.entity.CreditMemo;

@Service("creditMemoServiceImpl")
public class CreditMemoServiceImpl extends GenericSRIServiceImpl<NotaCreditoDTO, NotaCredito, CreditMemo> {

	@Override
	public CreditMemo toEntity(NotaCredito notaCredito, String contentAsXML) throws VeronicaException {
		CreditMemo creditMemo = new CreditMemo();
		creditMemo.setAccessKey(notaCredito.getInfoTributaria().getClaveAcceso());
		creditMemo.setSriVersion(notaCredito.getVersion());
		creditMemo.setXmlContent(contentAsXML);
		creditMemo.setSupplierId(notaCredito.getInfoTributaria().getRuc());
		creditMemo.setCustomerId(notaCredito.getInfoNotaCredito().getIdentificacionComprador());
		creditMemo.setIssueDate(DateUtils.getFechaFromStringddMMyyyy(notaCredito.getInfoNotaCredito().getFechaEmision()));
		creditMemo.setCreditMemoNumber(notaCredito.getInfoTributaria().getSecuencial());
		creditMemo.setInternalStatusId(CREATED);
		return creditMemo;
	}

	@Override
	@Autowired
	@Qualifier("cmJaxbConverter")
	public void setJaxbConverter(JaxbConverter cmJaxbConverter) {
		jaxbConverter = cmJaxbConverter;
	}

}