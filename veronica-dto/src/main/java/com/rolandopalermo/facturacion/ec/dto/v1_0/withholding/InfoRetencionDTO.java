package com.rolandopalermo.facturacion.ec.dto.v1_0.withholding;

import javax.validation.constraints.NotEmpty;

import com.rolandopalermo.facturacion.ec.dto.v1_0.InfoComprobanteDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoRetencionDTO extends InfoComprobanteDTO {

    @NotEmpty
    private String tipoIdentificacionSujetoRetenido;
    @NotEmpty
    private String razonSocialSujetoRetenido;
    @NotEmpty
    private String identificacionSujetoRetenido;
    private String periodoFiscal;

}