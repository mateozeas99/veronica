package com.rolandopalermo.facturacion.ec.dto;

import javax.validation.constraints.NotNull;

import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class EmpresaDTO {

	@NotEmpty
	private String numeroIdentificacion;
	@NotEmpty
	private String tipoIdentificacion;
	@NotEmpty
	private String razonSocial;
	@NotNull
	private byte[] logo;

}