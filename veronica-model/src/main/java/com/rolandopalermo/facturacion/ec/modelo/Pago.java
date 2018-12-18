package com.rolandopalermo.facturacion.ec.modelo;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

@Getter
@Setter
@XmlType(propOrder = {
        "formaPago",
        "total",
        "plazo",
        "unidadTiempo"
})
public class Pago {

    private String formaPago;
    private BigDecimal total;
    private String plazo;
    private String unidadTiempo;

}
