package com.rolandopalermo.facturacion.ec.dto.v1_0.bol;

import java.math.BigDecimal;
import java.util.List;

import com.rolandopalermo.facturacion.ec.dto.v1_0.DetAdicionalDTO;

import lombok.Getter;
import lombok.Setter;

 
@Getter
@Setter
public class GuiaDetallesDTO {
	
	private String codigoInterno;
	private String codigoAdicional;
	private String descripcion;
	private BigDecimal cantidad;
    private List<DetAdicionalDTO> detAdicional;
}
