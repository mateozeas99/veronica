package com.rolandopalermo.facturacion.ec.common.util;

public class Constants {

	/**
	 * Utility classes should not have a public constructor.
	 */
	private Constants() {
	}

	public static final String API_DOC_ANEXO_1 = "Ver ficha técnica - Anexo 1";
	
	public static final long CREATED = 1;
	public static final long POSTED = 2;
	public static final long APPLIED = 3;
	public static final long REJECTED = 4;
	public static final long INVALID = 5;
	
	public static final String SRI_REJECTED = "DEVUELTA";
	public static final String SRI_RECEIVED = "RECIBIDA";
	
	public static final String SRI_APPLIED = "AUTORIZADO";
	public static final String SRI_INVALID = "NO AUTORIZADO";

}
