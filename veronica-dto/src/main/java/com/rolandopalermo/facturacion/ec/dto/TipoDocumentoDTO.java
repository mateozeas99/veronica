package com.rolandopalermo.facturacion.ec.dto;

import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class TipoDocumentoDTO {

	@NotEmpty
	private String codigo;
	@NotEmpty
	private String descripcion;

}