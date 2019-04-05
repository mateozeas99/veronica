package com.rolandopalermo.facturacion.ec.dto.v1_0;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListaComprobantesDTO {

	private List<String> comprobantes = new ArrayList<>();

}