package com.rolandopalermo.facturacion.ec.service.v1_0.impl;

import static com.rolandopalermo.facturacion.ec.common.util.Constants.CREATED;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.common.converter.JaxbConverter;
import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.util.DateUtils;
import com.rolandopalermo.facturacion.ec.dto.v1_0.bol.GuiaRemisionDTO;
import com.rolandopalermo.facturacion.ec.modelo.guia.GuiaRemision;
import com.rolandopalermo.facturacion.ec.persistence.entity.Bol;
import com.rolandopalermo.facturacion.ec.persistence.entity.Consignee;

@Service("bolServiceImpl")
public class BolServiceImpl extends GenericSRIServiceImpl<GuiaRemisionDTO, GuiaRemision, Bol> {

	@Override
	public Bol toEntity(GuiaRemision guia, String contentAsXML) throws VeronicaException {
		Bol bol = new Bol();
		bol.setAccessKey(guia.getInfoTributaria().getClaveAcceso());
		bol.setSriVersion(guia.getVersion());
		bol.setXmlContent(contentAsXML);
		bol.setSupplierId(guia.getInfoTributaria().getRuc());
		bol.setShipperRuc(guia.getInfoGuiaRemision().getRucTransportista());
		bol.setRegistrationNumber(guia.getInfoGuiaRemision().getPlaca());
		bol.setIssueDate(DateUtils.getFechaFromStringddMMyyyy(guia.getInfoGuiaRemision().getFechaIniTransporte()));
		bol.setBolNumber(guia.getInfoTributaria().getSecuencial());
		bol.setInternalStatusId(CREATED);
		bol.setConsignees(guia.getDestinatario()
				.stream()
				.map(destinatario -> {
					Consignee consignee = new Consignee();
					consignee.setConsignneNumber(destinatario.getIdentificacionDestinatario());
					consignee.setCustomDocNumber(destinatario.getDocAduaneroUnico());
					consignee.setReferenceDocCod(destinatario.getCodDocSustento());
					consignee.setReferenceDocNumber(destinatario.getNumDocSustento());
					consignee.setReferenceDocAuthNumber(destinatario.getNumAutDocSustento());
					consignee.setBol(bol);
					return consignee;
				})
				.collect(Collectors.toList()));
		 return bol;
	}

	@Override
	@Autowired
	@Qualifier("bolJaxbConverter")
	public void setJaxbConverter(JaxbConverter bolJaxbConverter) {
		jaxbConverter = bolJaxbConverter;
	}

}