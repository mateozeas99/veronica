package com.rolandopalermo.facturacion.ec.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;
import com.rolandopalermo.facturacion.ec.common.sri.ClaveDeAcceso;
import com.rolandopalermo.facturacion.ec.common.util.DateUtils;
import com.rolandopalermo.facturacion.ec.dto.v1_0.CampoAdicionalDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.ComprobanteDTO;
import com.rolandopalermo.facturacion.ec.modelo.CampoAdicional;
import com.rolandopalermo.facturacion.ec.modelo.InfoTributaria;

public abstract class AbstractComprobanteMapper<DTO extends ComprobanteDTO> {

	private static final Logger logger = LogManager.getLogger(AbstractComprobanteMapper.class);

	protected InfoTributaria buildInfoTributaria(DTO dto) {
		InfoTributaria InfoTributaria = new InfoTributaria();
		InfoTributaria.setAmbiente(dto.getInfoTributaria().getAmbiente());
		InfoTributaria.setTipoEmision(dto.getInfoTributaria().getTipoEmision());
		InfoTributaria.setRazonSocial(dto.getInfoTributaria().getRazonSocial());
		InfoTributaria.setNombreComercial(dto.getInfoTributaria().getNombreComercial());
		InfoTributaria.setRuc(dto.getInfoTributaria().getRuc());
		InfoTributaria.setCodDoc(dto.getInfoTributaria().getCodDoc());
		InfoTributaria.setEstab(dto.getInfoTributaria().getEstab());
		InfoTributaria.setPtoEmi(dto.getInfoTributaria().getPtoEmi());
		InfoTributaria.setSecuencial(dto.getInfoTributaria().getSecuencial());
		InfoTributaria.setDirMatriz(dto.getInfoTributaria().getDirMatriz());
		return InfoTributaria;
	}

	protected List<CampoAdicional> buildCamposAdicionales(DTO dto) {
		List<CampoAdicionalDTO> infoAdicionalDTO = dto.getCampoAdicional();
		List<CampoAdicional> infoAdicional = infoAdicionalDTO.stream().map(campoAdicionalDTO -> {
			CampoAdicional campoAdicional = new CampoAdicional();
			campoAdicional.setNombre(campoAdicionalDTO.getNombre());
			campoAdicional.setValue(campoAdicionalDTO.getValue());
			return campoAdicional;
		}).collect(Collectors.toList());
		return infoAdicional;
	}

	protected String getClaveAcceso(final InfoTributaria infoTributaria, final String fechaEmision) {
		final StringBuilder sb = new StringBuilder(infoTributaria.getPtoEmi());
		sb.append(infoTributaria.getEstab());
		final String serie = sb.toString();
		final String codigoNumerico = RandomStringUtils.randomNumeric(8);
		try {
			return ClaveDeAcceso.builder()
					.fechaEmision(DateUtils.getFechaFromStringddMMyyyy(fechaEmision))
					.ambiente(infoTributaria.getAmbiente())
					.codigoNumerico(codigoNumerico)
					.numeroComprobante(infoTributaria.getSecuencial())
					.ruc(infoTributaria.getRuc())
					.serie(serie)
					.tipoComprobante(infoTributaria.getCodDoc())
					.tipoEmision(infoTributaria.getTipoEmision())
					.build()
					.generarClaveAcceso();
		} catch (VeronicaException e) {
			logger.error("Error al generar la Clave de Acceso", e);
			return null;
		}
	}
	
	protected abstract String getFechaEmision(final DTO comprobanteDTO);

}