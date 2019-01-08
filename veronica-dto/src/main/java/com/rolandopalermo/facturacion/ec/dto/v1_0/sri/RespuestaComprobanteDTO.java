package com.rolandopalermo.facturacion.ec.dto.v1_0.sri;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RespuestaComprobanteDTO {

    private String claveAccesoConsultada;
    private String numeroComprobantes;
    private List<AutorizacionDTO> autorizaciones;

}