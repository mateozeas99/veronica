package com.rolandopalermo.facturacion.ec.dto.v1_0.invoice;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rolandopalermo.facturacion.ec.dto.v1_0.DetAdicionalDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.ImpuestoDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({
        "codigoPrincipal",
        "codigoAuxiliar",
        "descripcion",
        "cantidad",
        "precioUnitario",
        "descuento",
        "precioTotalSinImpuesto",
        "detAdicional",
        "impuesto"
})
public class FacturaDetalleDTO {

    private String codigoPrincipal;
    private String codigoAuxiliar;
    @NotEmpty
    private String descripcion;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal descuento;
    private BigDecimal precioTotalSinImpuesto;
    private List<DetAdicionalDTO> detAdicional;
    @NotEmpty
    @Valid
    private List<ImpuestoDTO> impuesto;

}