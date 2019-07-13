package com.rolandopalermo.facturacion.ec.mapper.dm;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rolandopalermo.facturacion.ec.dto.v1_0.CampoAdicionalDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.InfoTributariaDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.dm.InfoNotaDebitoDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.dm.MotivoDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.dm.NotaDebitoDTO;
import com.rolandopalermo.facturacion.ec.mapper.AbstractComprobanteMapper;
import com.rolandopalermo.facturacion.ec.mapper.Mapper;
import com.rolandopalermo.facturacion.ec.modelo.CampoAdicional;
import com.rolandopalermo.facturacion.ec.modelo.InfoTributaria;
import com.rolandopalermo.facturacion.ec.modelo.notadebito.InfoNotaDebito;
import com.rolandopalermo.facturacion.ec.modelo.notadebito.Motivo;
import com.rolandopalermo.facturacion.ec.modelo.notadebito.NotaDebito;

@Component("notaDebitoMapper")
public class NotaDebitoMapper extends AbstractComprobanteMapper<NotaDebitoDTO>
		implements Mapper<NotaDebitoDTO, NotaDebito> {

	private Mapper<InfoTributariaDTO, InfoTributaria> InfoTributariaMapper;
	private Mapper<CampoAdicionalDTO, CampoAdicional> campoAdicionalMapper;
	private Mapper<InfoNotaDebitoDTO, InfoNotaDebito> infoNotaDebitoMapper;
	private Mapper<MotivoDTO, Motivo> motivoMapper;

	@Override
	public NotaDebito convert(final NotaDebitoDTO notaDebitoDTO) {
		if (notaDebitoDTO == null) {
			return null;
		}
		final NotaDebito notaDebito = new NotaDebito();
		notaDebito.setId(notaDebitoDTO.getId());
		notaDebito.setVersion(notaDebitoDTO.getVersion());
		final InfoTributaria infoTributaria = getInfoTributariaMapper().convert(notaDebitoDTO.getInfoTributaria());
		if (infoTributaria != null) {
			infoTributaria.setClaveAcceso(getClaveAcceso(infoTributaria, getFechaEmision(notaDebitoDTO)));
			notaDebito.setInfoTributaria(infoTributaria);
		}
		notaDebito.setCampoAdicional(getCampoAdicionalMapper().convertAll(notaDebitoDTO.getCampoAdicional()));
		notaDebito.setInfoNotaDebito(getInfoNotaDebitoMapper().convert(notaDebitoDTO.getInfoNotaDebito()));
		notaDebito.setMotivo(getMotivoMapper().convertAll(notaDebitoDTO.getMotivo()));
		return notaDebito;
	}

	protected Mapper<InfoTributariaDTO, InfoTributaria> getInfoTributariaMapper() {
		return InfoTributariaMapper;
	}

	@Autowired
	@Qualifier("infoTributariaMapper")
	public void setInfoTributariaMapper(Mapper<InfoTributariaDTO, InfoTributaria> infoTributariaMapper) {
		InfoTributariaMapper = infoTributariaMapper;
	}

	protected Mapper<CampoAdicionalDTO, CampoAdicional> getCampoAdicionalMapper() {
		return campoAdicionalMapper;
	}

	@Autowired
	@Qualifier("campoAdicionalMapper")
	public void setCampoAdicionalMapper(Mapper<CampoAdicionalDTO, CampoAdicional> campoAdicionalMapper) {
		this.campoAdicionalMapper = campoAdicionalMapper;
	}

	@Override
	protected String getFechaEmision(final NotaDebitoDTO notaDebitoDTO) {
		return Optional.ofNullable(notaDebitoDTO).map(NotaDebitoDTO::getInfoNotaDebito)
				.map(InfoNotaDebitoDTO::getFechaEmision).orElse(null);
	}

	protected Mapper<InfoNotaDebitoDTO, InfoNotaDebito> getInfoNotaDebitoMapper() {
		return infoNotaDebitoMapper;
	}

	@Autowired
	@Qualifier("infoNotaDebitoMapper")
	public void setInfoNotaDebitoMapper(Mapper<InfoNotaDebitoDTO, InfoNotaDebito> infoNotaDebitoMapper) {
		this.infoNotaDebitoMapper = infoNotaDebitoMapper;
	}

	public Mapper<MotivoDTO, Motivo> getMotivoMapper() {
		return motivoMapper;
	}

	@Autowired
	@Qualifier("motivoMapper")
	public void setMotivoMapper(Mapper<MotivoDTO, Motivo> motivoMapper) {
		this.motivoMapper = motivoMapper;
	}

}