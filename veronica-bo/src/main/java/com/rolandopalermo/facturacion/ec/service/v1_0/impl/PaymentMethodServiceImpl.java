package com.rolandopalermo.facturacion.ec.service.v1_0.impl;

import org.springframework.stereotype.Service;

import com.rolandopalermo.facturacion.ec.dto.v1_0.MetodoPagoDTO;
import com.rolandopalermo.facturacion.ec.persistence.entity.PaymentMethod;

@Service("paymentMethodServiceImpl")
public class PaymentMethodServiceImpl extends GenericOperationServiceImpl<PaymentMethod, MetodoPagoDTO> {

	@Override
	public PaymentMethod mapTo(MetodoPagoDTO metodoPagoDTO) {
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setCode(metodoPagoDTO.getCodigo());
		paymentMethod.setDescription(metodoPagoDTO.getDescripcion());
		return paymentMethod;
	}
	
}