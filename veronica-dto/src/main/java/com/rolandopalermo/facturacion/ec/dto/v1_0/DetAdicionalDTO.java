package com.rolandopalermo.facturacion.ec.dto.v1_0;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetAdicionalDTO {

    @NotEmpty
    private String nombre;
    @NotEmpty
    private String valor;

}