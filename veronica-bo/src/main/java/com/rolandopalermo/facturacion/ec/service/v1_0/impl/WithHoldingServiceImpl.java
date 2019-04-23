package com.rolandopalermo.facturacion.ec.service.v1_0.impl;

import static com.rolandopalermo.facturacion.ec.common.util.Constants.CREATED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.common.converter.JaxbConverter;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.util.DateUtils;
import com.rolandopalermo.facturacion.ec.dto.v1_0.withholding.RetencionDTO;
import com.rolandopalermo.facturacion.ec.modelo.retencion.ComprobanteRetencion;
import com.rolandopalermo.facturacion.ec.persistence.entity.Withholding;

@Service("withHoldingServiceImpl")
public class WithHoldingServiceImpl extends GenericSRIServiceImpl<RetencionDTO, ComprobanteRetencion, Withholding> {

	@Override
	public Withholding toEntity(ComprobanteRetencion comprobanteRetencion, String contentAsXML) throws VeronicaException {
		Withholding withHolding = new Withholding();
		withHolding.setAccessKey(comprobanteRetencion.getInfoTributaria().getClaveAcceso());
		withHolding.setSriVersion(comprobanteRetencion.getVersion());
		withHolding.setXmlContent(new String(contentAsXML));
		withHolding.setSupplierId(comprobanteRetencion.getInfoTributaria().getRuc());
		withHolding.setCustomerId(comprobanteRetencion.getInfoCompRetencion().getIdentificacionSujetoRetenido());
		withHolding.setIssueDate(DateUtils.getFechaFromStringddMMyyyy(comprobanteRetencion.getInfoCompRetencion().getFechaEmision()));
		withHolding.setInternalStatusId(CREATED);
		return withHolding;
	}

	@Override
	@Autowired
	@Qualifier("withHoldingJaxbConverter")
	public void setJaxbConverter(JaxbConverter withHoldingJaxbConverter) {
		jaxbConverter = withHoldingJaxbConverter;
	}

}