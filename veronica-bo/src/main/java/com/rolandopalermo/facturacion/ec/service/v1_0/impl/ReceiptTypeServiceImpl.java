package com.rolandopalermo.facturacion.ec.service.v1_0.impl;

import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.dto.v1_0.TipoDocumentoRetenidoDTO;
import com.rolandopalermo.facturacion.ec.persistence.entity.ReceiptType;

@Service("receiptTypeServiceImpl")
public class ReceiptTypeServiceImpl extends GenericCRUDServiceImpl<ReceiptType, TipoDocumentoRetenidoDTO> {

	@Override
	public ReceiptType mapTo(TipoDocumentoRetenidoDTO tipoDocumentoRetenidoDTO) {
		ReceiptType receiptType = new ReceiptType();
		receiptType.setCode(tipoDocumentoRetenidoDTO.getCodigo());
		receiptType.setDescription(tipoDocumentoRetenidoDTO.getDescripcion());
		return receiptType;
	}

	@Override
	public TipoDocumentoRetenidoDTO build(ReceiptType domainObject) {
		return null;
	}
	
}