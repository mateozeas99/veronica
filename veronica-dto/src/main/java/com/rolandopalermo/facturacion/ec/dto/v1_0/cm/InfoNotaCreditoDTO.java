package com.rolandopalermo.facturacion.ec.dto.v1_0.cm;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rolandopalermo.facturacion.ec.dto.v1_0.InfoComprobanteDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.TotalImpuestoDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoNotaCreditoDTO extends InfoComprobanteDTO {

	private String tipoIdentificacionComprador;
	private String razonSocialComprador;
	private String identificacionComprador;
	private String rise;
	private String codDocModificado;
	private String numDocModificado;
	private String fechaEmisionDocSustento;
	private BigDecimal totalSinImpuestos;
	private BigDecimal valorModificacion;
	private String moneda;
	private String motivo;
	@NotNull
	@Valid
	@Size(min = 1)
	private List<TotalImpuestoDTO> totalImpuesto;

}