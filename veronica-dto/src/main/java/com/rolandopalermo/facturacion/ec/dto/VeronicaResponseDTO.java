package com.rolandopalermo.facturacion.ec.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeronicaResponseDTO<T> {

	private boolean success;
	private T result;
	
	public VeronicaResponseDTO() {
		this.success = true;
	}
	
	public VeronicaResponseDTO(T result) {
		this.success = true;
	}
	
}