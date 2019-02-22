package com.rolandopalermo.facturacion.ec.mapper.withholding;

import org.springframework.stereotype.Component;

import com.rolandopalermo.facturacion.ec.dto.v1_0.withholding.InfoRetencionDTO;
import com.rolandopalermo.facturacion.ec.mapper.Mapper;
import com.rolandopalermo.facturacion.ec.modelo.retencion.InfoCompRetencion;

@Component("infoCompRetencionMapper")
public class InfoCompRetencionMapper implements Mapper<InfoRetencionDTO, InfoCompRetencion> {
	
    @Override
    public InfoCompRetencion convert(final InfoRetencionDTO infoRetencionDTO) {
        if (infoRetencionDTO == null) {
            return null;
        }
        final InfoCompRetencion infoCompRetencion = new InfoCompRetencion();
        infoCompRetencion.setFechaEmision(infoRetencionDTO.getFechaEmision());
        infoCompRetencion.setDirEstablecimiento(infoRetencionDTO.getDirEstablecimiento());
        infoCompRetencion.setContribuyenteEspecial(infoRetencionDTO.getContribuyenteEspecial());
        infoCompRetencion.setObligadoContabilidad(infoRetencionDTO.getObligadoContabilidad());
        infoCompRetencion.setTipoIdentificacionSujetoRetenido(infoRetencionDTO.getTipoIdentificacionSujetoRetenido());
        infoCompRetencion.setIdentificacionSujetoRetenido(infoRetencionDTO.getIdentificacionSujetoRetenido());
        infoCompRetencion.setRazonSocialSujetoRetenido(infoRetencionDTO.getRazonSocialSujetoRetenido());
        infoCompRetencion.setPeriodoFiscal(infoRetencionDTO.getPeriodoFiscal());
        return infoCompRetencion;
    }
    
}