package com.rolandopalermo.facturacion.ec.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class VeronicaException extends RuntimeException {

	private static final long serialVersionUID = 4956405604792374198L;

	public VeronicaException(String message) {
		super(message);
	}

	public VeronicaException(Exception ex) {
		super(ex);
	}

}