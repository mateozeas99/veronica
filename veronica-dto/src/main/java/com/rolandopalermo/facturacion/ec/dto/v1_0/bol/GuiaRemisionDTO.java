package com.rolandopalermo.facturacion.ec.dto.v1_0.bol;

import java.util.List;

import com.rolandopalermo.facturacion.ec.dto.v1_0.ComprobanteDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuiaRemisionDTO extends ComprobanteDTO {

	private InfoGuiaRemisionDTO infoGuiaRemisionDTO;
	private List<DestinatarioDTO> destinatario;

}
