package com.rolandopalermo.facturacion.ec.dto.v1_0;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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