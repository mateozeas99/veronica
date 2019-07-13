package com.rolandopalermo.facturacion.ec.dto.v1_0.dm;

import java.util.List;

import com.rolandopalermo.facturacion.ec.dto.v1_0.ComprobanteDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotaDebitoDTO extends ComprobanteDTO {

	private InfoNotaDebitoDTO infoNotaDebito;
	private List<MotivoDTO> motivo;

}