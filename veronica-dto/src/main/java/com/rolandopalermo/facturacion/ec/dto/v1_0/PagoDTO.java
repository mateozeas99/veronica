package com.rolandopalermo.facturacion.ec.dto.v1_0;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagoDTO {

    private String formaPago;
    private BigDecimal total;
    private String plazo;
    private String unidadTiempo;

}