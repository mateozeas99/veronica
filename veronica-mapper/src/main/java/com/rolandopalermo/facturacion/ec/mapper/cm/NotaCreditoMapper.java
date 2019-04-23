package com.rolandopalermo.facturacion.ec.mapper.cm;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rolandopalermo.facturacion.ec.dto.v1_0.CampoAdicionalDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.InfoTributariaDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.cm.InfoNotaCreditoDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.cm.NotaCreditoDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.cm.NotaCreditoDetalleDTO;
import com.rolandopalermo.facturacion.ec.mapper.AbstractComprobanteMapper;
import com.rolandopalermo.facturacion.ec.mapper.Mapper;
import com.rolandopalermo.facturacion.ec.modelo.CampoAdicional;
import com.rolandopalermo.facturacion.ec.modelo.InfoTributaria;
import com.rolandopalermo.facturacion.ec.modelo.notacredito.Detalle;
import com.rolandopalermo.facturacion.ec.modelo.notacredito.InfoNotaCredito;
import com.rolandopalermo.facturacion.ec.modelo.notacredito.NotaCredito;

@Component("notaCreditoMapper")
public class NotaCreditoMapper extends AbstractComprobanteMapper<NotaCreditoDTO>
		implements Mapper<NotaCreditoDTO, NotaCredito> {

	private Mapper<InfoTributariaDTO, InfoTributaria> InfoTributariaMapper;
	private Mapper<CampoAdicionalDTO, CampoAdicional> campoAdicionalMapper;
	private Mapper<InfoNotaCreditoDTO, InfoNotaCredito> infoNotaCreditoMapper;
	private Mapper<NotaCreditoDetalleDTO, Detalle> notaCreditoDetalleMapper;

	@Override
	public NotaCredito convert(final NotaCreditoDTO notaCreditoDTO) {
		if (notaCreditoDTO == null) {
			return null;
		}
		final NotaCredito notaCredito = new NotaCredito();
		notaCredito.setId(notaCreditoDTO.getId());
		notaCredito.setVersion(notaCreditoDTO.getVersion());
		final InfoTributaria infoTributaria = getInfoTributariaMapper().convert(notaCreditoDTO.getInfoTributaria());
		if (infoTributaria != null) {
			infoTributaria.setClaveAcceso(getClaveAcceso(infoTributaria, getFechaEmision(notaCreditoDTO)));
			notaCredito.setInfoTributaria(infoTributaria);
		}
		notaCredito.setCampoAdicional(getCampoAdicionalMapper().convertAll(notaCreditoDTO.getCampoAdicional()));
		notaCredito.setInfoNotaCredito(getInfoNotaCreditoMapper().convert(notaCreditoDTO.getInfoNotaCredito()));
		notaCredito.setDetalle(getNotaCreditoDetalleMapper().convertAll(notaCreditoDTO.getDetalle()));
		return notaCredito;
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
	protected String getFechaEmision(final NotaCreditoDTO notaCreditoDTO) {
		return Optional.ofNullable(notaCreditoDTO).map(NotaCreditoDTO::getInfoNotaCredito)
				.map(InfoNotaCreditoDTO::getFechaEmision).orElse(null);
	}

	protected Mapper<InfoNotaCreditoDTO, InfoNotaCredito> getInfoNotaCreditoMapper() {
		return infoNotaCreditoMapper;
	}

	@Autowired
	@Qualifier("infoNotaCreditoMapper")
	public void setInfoNotaCreditoMapper(Mapper<InfoNotaCreditoDTO, InfoNotaCredito> infoNotaCreditoMapper) {
		this.infoNotaCreditoMapper = infoNotaCreditoMapper;
	}
	
	protected Mapper<NotaCreditoDetalleDTO, Detalle> getNotaCreditoDetalleMapper() {
        return notaCreditoDetalleMapper;
    }

    @Autowired
    @Qualifier("notaCreditoDetalleMapper")
    public void setNotaCreditoDetalleMapper(Mapper<NotaCreditoDetalleDTO, Detalle> notaCreditoDetalleMapper) {
        this.notaCreditoDetalleMapper = notaCreditoDetalleMapper;
    }

}