package com.rolandopalermo.facturacion.ec.dto.v1_0;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalImpuestoDTO {

	@NotEmpty
	private String codigo;
	@NotEmpty
	private String codigoPorcentaje;
	private BigDecimal descuentoAdicional;
	@NotNull
	private BigDecimal baseImponible;
	private BigDecimal tarifa;
	@NotNull
	private BigDecimal valor;

}