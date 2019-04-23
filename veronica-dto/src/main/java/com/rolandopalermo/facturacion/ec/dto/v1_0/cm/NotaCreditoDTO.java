package com.rolandopalermo.facturacion.ec.dto.v1_0.cm;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rolandopalermo.facturacion.ec.dto.v1_0.ComprobanteDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotaCreditoDTO extends ComprobanteDTO {

	@Valid
	private InfoNotaCreditoDTO infoNotaCredito;
	@NotNull
	@Valid
	@Size(min = 1)
	private List<NotaCreditoDetalleDTO> detalle;
	
}