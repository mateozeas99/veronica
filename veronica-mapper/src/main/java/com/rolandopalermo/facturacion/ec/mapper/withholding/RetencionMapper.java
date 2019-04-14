package com.rolandopalermo.facturacion.ec.mapper.withholding;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rolandopalermo.facturacion.ec.dto.v1_0.CampoAdicionalDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.ImpuestoDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.InfoTributariaDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.withholding.InfoRetencionDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.withholding.RetencionDTO;
import com.rolandopalermo.facturacion.ec.mapper.AbstractComprobanteMapper;
import com.rolandopalermo.facturacion.ec.mapper.Mapper;
import com.rolandopalermo.facturacion.ec.modelo.CampoAdicional;
import com.rolandopalermo.facturacion.ec.modelo.InfoTributaria;
import com.rolandopalermo.facturacion.ec.modelo.retencion.ComprobanteRetencion;
import com.rolandopalermo.facturacion.ec.modelo.retencion.Impuesto;
import com.rolandopalermo.facturacion.ec.modelo.retencion.InfoCompRetencion;

@Component("retencionMapper")
public class RetencionMapper extends AbstractComprobanteMapper<RetencionDTO> implements Mapper<RetencionDTO, ComprobanteRetencion> {

	private Mapper<CampoAdicionalDTO, CampoAdicional> campoAdicionalMapper;
	private Mapper<InfoTributariaDTO, InfoTributaria> infoTributariaMapper;
	private Mapper<InfoRetencionDTO, InfoCompRetencion> infoCompRetencionMapper;
	private Mapper<ImpuestoDTO, Impuesto> impuestoMapper;

	@Override
	public ComprobanteRetencion convert(final RetencionDTO retencionDTO) {
		if (retencionDTO == null) {
			return null;
		}
		ComprobanteRetencion comprobanteRetencion = new ComprobanteRetencion();
		comprobanteRetencion.setCampoAdicional(getCampoAdicionalMapper().convertAll(retencionDTO.getCampoAdicional()));
		comprobanteRetencion.setId(retencionDTO.getId());
		comprobanteRetencion.setVersion(retencionDTO.getVersion());
		comprobanteRetencion.setInfoCompRetencion(getInfoCompRetencionMapper().convert(retencionDTO.getInfoRetencion()));
		final InfoTributaria infoTributaria = getInfoTributariaMapper().convert(retencionDTO.getInfoTributaria());
		if (infoTributaria != null) {
			infoTributaria.setClaveAcceso(getClaveAcceso(infoTributaria, getFechaEmision(retencionDTO)));
			comprobanteRetencion.setInfoTributaria(infoTributaria);
		}
		comprobanteRetencion.setImpuesto(getImpuestoMapper().convertAll(retencionDTO.getImpuesto()));
		return comprobanteRetencion;
	}

	@Override
	protected String getFechaEmision(final RetencionDTO comprobanteDTO) {
		return Optional.ofNullable(comprobanteDTO).map(RetencionDTO::getInfoRetencion).map(InfoRetencionDTO::getFechaEmision).orElse(null);
	}

	protected Mapper<CampoAdicionalDTO, CampoAdicional> getCampoAdicionalMapper() {
		return campoAdicionalMapper;
	}
	
	public Mapper<ImpuestoDTO, Impuesto> getImpuestoMapper() {
		return impuestoMapper;
	}
	
	@Autowired
	@Qualifier("impuestoRetencionMapper")
	public void setImpuestoMapper(Mapper<ImpuestoDTO, Impuesto> impuestoMapper) {
		this.impuestoMapper = impuestoMapper;
	}

	@Autowired
	@Qualifier("campoAdicionalMapper")
	public void setCampoAdicionalMapper(Mapper<CampoAdicionalDTO, CampoAdicional> campoAdicionalMapper) {
		this.campoAdicionalMapper = campoAdicionalMapper;
	}

	protected Mapper<InfoTributariaDTO, InfoTributaria> getInfoTributariaMapper() {
		return infoTributariaMapper;
	}

	@Autowired
	@Qualifier("infoTributariaMapper")
	public void setInfoTributariaMapper(Mapper<InfoTributariaDTO, InfoTributaria> infoTributariaMapper) {
		this.infoTributariaMapper = infoTributariaMapper;
	}

	protected Mapper<InfoRetencionDTO, InfoCompRetencion> getInfoCompRetencionMapper() {
		return infoCompRetencionMapper;
	}

	@Autowired
	@Qualifier("infoCompRetencionMapper")
	public void setInfoCompRetencionMapper(Mapper<InfoRetencionDTO, InfoCompRetencion> infoCompRetencionMapper) {
		this.infoCompRetencionMapper = infoCompRetencionMapper;
	}

}