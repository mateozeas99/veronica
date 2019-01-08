package com.rolandopalermo.facturacion.ec.dto.v1_0.invoice;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rolandopalermo.facturacion.ec.dto.v1_0.ComprobanteDTO;

import java.util.List;

@Getter
@Setter
public class FacturaDTO extends ComprobanteDTO {
	@Valid
	private InfoFacturaDTO infoFactura;
	@NotNull
	@Valid
	@Size(min = 1)
	private List<FacturaDetalleDTO> detalle;

}