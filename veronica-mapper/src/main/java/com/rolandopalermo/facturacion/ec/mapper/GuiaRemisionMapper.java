package com.rolandopalermo.facturacion.ec.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rolandopalermo.facturacion.ec.dto.v1_0.CampoAdicionalDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.InfoTributariaDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.bol.DestinatarioDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.bol.GuiaRemisionDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.bol.InfoGuiaRemisionDTO;
import com.rolandopalermo.facturacion.ec.modelo.CampoAdicional;
import com.rolandopalermo.facturacion.ec.modelo.InfoTributaria;
import com.rolandopalermo.facturacion.ec.modelo.guia.Destinatario;
import com.rolandopalermo.facturacion.ec.modelo.guia.GuiaRemision;
import com.rolandopalermo.facturacion.ec.modelo.guia.InfoGuiaRemision;

@Component("guiaRemisionMapper")
public class GuiaRemisionMapper extends AbstractComprobanteMapper<GuiaRemisionDTO> implements Mapper<GuiaRemisionDTO, GuiaRemision> {

	private Mapper<CampoAdicionalDTO, CampoAdicional> campoAdicionalMapper;
	private Mapper<DestinatarioDTO, Destinatario> destinatarioMapper;
	private Mapper<InfoGuiaRemisionDTO, InfoGuiaRemision> infoGuiaRemisionMapper;
	private Mapper<InfoTributariaDTO, InfoTributaria> infoTributariaMapper;

	@Override
	public GuiaRemision convert(final GuiaRemisionDTO guiaRemisionDTO) {
		if (guiaRemisionDTO == null) {
			return null;
		}
		final GuiaRemision guiaRemision = new GuiaRemision();
		guiaRemision.setId(guiaRemisionDTO.getId());
		guiaRemision.setVersion(guiaRemisionDTO.getVersion());
		guiaRemision.setCampoAdicional(getCampoAdicionalMapper().convertAll(guiaRemisionDTO.getCampoAdicional()));
		guiaRemision.setDestinatario(getDestinatarioMapper().convertAll(guiaRemisionDTO.getDestinatario()));
		guiaRemision.setInfoGuiaRemision(getInfoGuiaRemisionMapper().convert(guiaRemisionDTO.getInfoGuiaRemisionDTO()));
		final InfoTributaria infoTributaria = getInfoTributariaMapper().convert(guiaRemisionDTO.getInfoTributaria());
		if (infoTributaria != null) {
			infoTributaria.setClaveAcceso(getClaveAcceso(infoTributaria, getFechaEmision(guiaRemisionDTO)));
			guiaRemision.setInfoTributaria(infoTributaria);
		}
		return guiaRemision;
	}

	@Override
	protected String getFechaEmision(final GuiaRemisionDTO comprobanteDTO) {
		return Optional.ofNullable(comprobanteDTO).map(GuiaRemisionDTO::getInfoGuiaRemisionDTO).map(InfoGuiaRemisionDTO::getFechaIniTransporte).orElse(null);
	}

	protected Mapper<CampoAdicionalDTO, CampoAdicional> getCampoAdicionalMapper() {
		return campoAdicionalMapper;
	}

	@Autowired
	@Qualifier("campoAdicionalMapper")
	public void setCampoAdicionalMapper(Mapper<CampoAdicionalDTO, CampoAdicional> campoAdicionalMapper) {
		this.campoAdicionalMapper = campoAdicionalMapper;
	}

	protected Mapper<DestinatarioDTO, Destinatario> getDestinatarioMapper() {
		return destinatarioMapper;
	}

	@Autowired
	@Qualifier("destinatarioMapper")
	public void setDestinatarioMapper(Mapper<DestinatarioDTO, Destinatario> destinatarioMapper) {
		this.destinatarioMapper = destinatarioMapper;
	}

	protected Mapper<InfoGuiaRemisionDTO, InfoGuiaRemision> getInfoGuiaRemisionMapper() {
		return infoGuiaRemisionMapper;
	}

	@Autowired
	@Qualifier("infoGuiaRemisionMapper")
	public void setInfoGuiaRemisionMapper(Mapper<InfoGuiaRemisionDTO, InfoGuiaRemision> infoGuiaRemisionMapper) {
		this.infoGuiaRemisionMapper = infoGuiaRemisionMapper;
	}

	protected Mapper<InfoTributariaDTO, InfoTributaria> getInfoTributariaMapper() {
		return infoTributariaMapper;
	}

	@Autowired
	@Qualifier("infoTributariaMapper")
	public void setInfoTributariaMapper(Mapper<InfoTributariaDTO, InfoTributaria> infoTributariaMapper) {
		this.infoTributariaMapper = infoTributariaMapper;
	}

}