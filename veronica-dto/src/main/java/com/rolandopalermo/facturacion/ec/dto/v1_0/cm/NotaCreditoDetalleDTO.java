package com.rolandopalermo.facturacion.ec.dto.v1_0.cm;

import java.math.BigDecimal;
import java.util.List;

import com.rolandopalermo.facturacion.ec.dto.v1_0.DetAdicionalDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.ImpuestoDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotaCreditoDetalleDTO {

	private String codigoInterno;
	private String codigoAdicional;
	private String descripcion;
	private BigDecimal cantidad;
	private BigDecimal precioUnitario;
	private BigDecimal descuento;
	private BigDecimal precioTotalSinImpuesto;
	private List<DetAdicionalDTO> detAdicional;
	private List<ImpuestoDTO> impuesto;

}