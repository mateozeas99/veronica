package com.rolandopalermo.facturacion.ec.dto.v1_0.sri;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RespuestaSolicitudDTO {

    private String estado;
    private List<ComprobanteRespuestaDTO> comprobantes;

}