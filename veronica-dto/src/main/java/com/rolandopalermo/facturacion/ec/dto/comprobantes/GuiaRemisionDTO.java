package com.rolandopalermo.facturacion.ec.dto.comprobantes;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuiaRemisionDTO extends ComprobanteDTO {

	private InfoGuiaRemisionDTO infoGuiaRemisionDTO;
	private List<DestinatarioDTO> destinatario;

}
