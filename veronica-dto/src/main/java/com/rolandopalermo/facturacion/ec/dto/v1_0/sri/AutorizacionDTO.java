package com.rolandopalermo.facturacion.ec.dto.v1_0.sri;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Builder
public class AutorizacionDTO {

    private final String estado;
    private final String numeroAutorizacion;
    private final String fechaAutorizacion;
    private final String ambiente;
    @JsonIgnore
    private final String comprobante;
    private final List<MensajeDTO> mensajes;

}