package com.rolandopalermo.facturacion.ec.service.v1_0.impl;

import static com.rolandopalermo.facturacion.ec.common.util.Constants.CREATED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.common.converter.JaxbConverter;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.util.DateUtils;
import com.rolandopalermo.facturacion.ec.dto.v1_0.dm.NotaDebitoDTO;
import com.rolandopalermo.facturacion.ec.modelo.notadebito.NotaDebito;
import com.rolandopalermo.facturacion.ec.persistence.entity.DebitMemo;

@Service("debitMemoServiceImpl")
public class DebitMemoServiceImpl extends GenericSRIServiceImpl<NotaDebitoDTO, NotaDebito, DebitMemo> {

	@Override
	public DebitMemo toEntity(NotaDebito notaDebito, String contentAsXML) throws VeronicaException {
		DebitMemo creditMemo = new DebitMemo();
		creditMemo.setAccessKey(notaDebito.getInfoTributaria().getClaveAcceso());
		creditMemo.setSriVersion(notaDebito.getVersion());
		creditMemo.setXmlContent(contentAsXML);
		creditMemo.setSupplierId(notaDebito.getInfoTributaria().getRuc());
		creditMemo.setCustomerId(notaDebito.getInfoNotaDebito().getIdentificacionComprador());
		creditMemo.setIssueDate(DateUtils.getFechaFromStringddMMyyyy(notaDebito.getInfoNotaDebito().getFechaEmision()));
		creditMemo.setDebitMemoNumber(notaDebito.getInfoTributaria().getSecuencial());
		creditMemo.setInternalStatusId(CREATED);
		return creditMemo;
	}

	@Override
	@Autowired
	@Qualifier("dmJaxbConverter")
	public void setJaxbConverter(JaxbConverter dmJaxbConverter) {
		jaxbConverter = dmJaxbConverter;
	}

}