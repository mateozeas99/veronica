package com.rolandopalermo.facturacion.ec.dto.v1_0;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoComprobanteDTO {

    @NotEmpty
    private String fechaEmision;
    @NotEmpty
    private String dirEstablecimiento;
    private String contribuyenteEspecial;
    private String obligadoContabilidad;

}