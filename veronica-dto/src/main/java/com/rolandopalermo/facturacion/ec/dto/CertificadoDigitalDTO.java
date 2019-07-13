package com.rolandopalermo.facturacion.ec.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertificadoDigitalDTO {

	@NotEmpty
	private String password;
	@NotEmpty
	private String rucPropietario;
	@NotNull
	private byte[] archivo;

}