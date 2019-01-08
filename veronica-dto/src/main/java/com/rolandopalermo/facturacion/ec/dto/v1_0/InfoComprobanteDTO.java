package com.rolandopalermo.facturacion.ec.dto.v1_0;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;

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