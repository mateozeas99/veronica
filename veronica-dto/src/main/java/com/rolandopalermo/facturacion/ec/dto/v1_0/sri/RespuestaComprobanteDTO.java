package com.rolandopalermo.facturacion.ec.dto.v1_0.sri;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
public class RespuestaComprobanteDTO {

	private Long timestamp;
    private String claveAccesoConsultada;
    private String numeroComprobantes;
    @JsonIgnore
    private String contentAsXML;
    private List<AutorizacionDTO> autorizaciones;

}