package com.rolandopalermo.facturacion.ec.common.util;

public enum DocumentType {

	FACTURA("01"),
    NOTA_CREDITO("04"),
    NOTA_DEBITO("05"),
    GUITA_REMISION("06"),
    COMPROBANTE_RETENCION("07");
    
    private String code;
	
	DocumentType(String code) {
        this.code = code;
    }
	
	public String code() {
        return code;
    }
    
}