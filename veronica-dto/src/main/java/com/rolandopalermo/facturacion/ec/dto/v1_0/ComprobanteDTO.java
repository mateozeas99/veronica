package com.rolandopalermo.facturacion.ec.dto.v1_0;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ComprobanteDTO {

    @NotEmpty
    private String id;
    @NotEmpty
    private String version;
    @NotNull
    @Valid
    private InfoTributariaDTO infoTributaria;
    @Valid
    private List<CampoAdicionalDTO> campoAdicional;

}