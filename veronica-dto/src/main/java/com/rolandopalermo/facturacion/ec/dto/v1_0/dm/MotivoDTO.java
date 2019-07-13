package com.rolandopalermo.facturacion.ec.dto.v1_0.dm;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MotivoDTO {

	private String razon;
	private BigDecimal valor;

}