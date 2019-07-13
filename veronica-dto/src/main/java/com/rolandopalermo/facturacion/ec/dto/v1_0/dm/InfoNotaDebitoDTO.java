package com.rolandopalermo.facturacion.ec.dto.v1_0.dm;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.rolandopalermo.facturacion.ec.dto.v1_0.ImpuestoDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.InfoComprobanteDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.PagoDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoNotaDebitoDTO extends InfoComprobanteDTO {

	private String tipoIdentificacionComprador;
	private String razonSocialComprador;
	private String identificacionComprador;
	private String codDocModificado;
	private String numDocModificado;
	private String fechaEmisionDocSustento;
	private String rise;
	private BigDecimal totalSinImpuestos;
	protected BigDecimal valorTotal;
	@NotNull
    @Valid
    @Size(min = 1)
    private List<ImpuestoDTO> impuesto;
	@NotEmpty
	@Valid
	private List<PagoDTO> pagos;

}