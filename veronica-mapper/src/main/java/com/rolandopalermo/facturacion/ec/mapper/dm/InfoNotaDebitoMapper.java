package com.rolandopalermo.facturacion.ec.mapper.dm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rolandopalermo.facturacion.ec.dto.v1_0.ImpuestoDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.dm.InfoNotaDebitoDTO;
import com.rolandopalermo.facturacion.ec.mapper.Mapper;
import com.rolandopalermo.facturacion.ec.modelo.notadebito.Impuesto;
import com.rolandopalermo.facturacion.ec.modelo.notadebito.InfoNotaDebito;

@Component("infoNotaDebitoMapper")
public class InfoNotaDebitoMapper implements Mapper<InfoNotaDebitoDTO, InfoNotaDebito> {

    private Mapper<ImpuestoDTO, Impuesto> impuestoNotaDebitoMapper;

    @Override
    public InfoNotaDebito convert(final InfoNotaDebitoDTO infoNotaDebitoDTO) {
        final InfoNotaDebito infoNotaDebito = new InfoNotaDebito();
        infoNotaDebito.setFechaEmision(infoNotaDebitoDTO.getFechaEmision());
        infoNotaDebito.setDirEstablecimiento(infoNotaDebitoDTO.getDirEstablecimiento());
        infoNotaDebito.setContribuyenteEspecial(infoNotaDebitoDTO.getContribuyenteEspecial());
        infoNotaDebito.setObligadoContabilidad(infoNotaDebitoDTO.getObligadoContabilidad());
        infoNotaDebito.setRise(infoNotaDebitoDTO.getRise());
        infoNotaDebito.setTipoIdentificacionComprador(infoNotaDebitoDTO.getTipoIdentificacionComprador());
        infoNotaDebito.setRazonSocialComprador(infoNotaDebitoDTO.getRazonSocialComprador());
        infoNotaDebito.setIdentificacionComprador(infoNotaDebitoDTO.getIdentificacionComprador());
        infoNotaDebito.setTotalSinImpuestos(infoNotaDebitoDTO.getTotalSinImpuestos());
        infoNotaDebito.setCodDocModificado(infoNotaDebitoDTO.getCodDocModificado());
        infoNotaDebito.setNumDocModificado(infoNotaDebitoDTO.getNumDocModificado());
        infoNotaDebito.setFechaEmisionDocSustento(infoNotaDebitoDTO.getFechaEmisionDocSustento());
        infoNotaDebito.setImpuesto(getTotalImpuestoMapper().convertAll(infoNotaDebitoDTO.getImpuesto()));
        infoNotaDebito.setValorTotal(infoNotaDebitoDTO.getValorTotal());
        return infoNotaDebito;
    }

    protected Mapper<ImpuestoDTO, Impuesto> getTotalImpuestoMapper() {
        return impuestoNotaDebitoMapper;
    }

    @Autowired
    @Qualifier("impuestoNotaDebitoMapper")
    public void setTotalImpuestoMapper(Mapper<ImpuestoDTO, Impuesto> impuestoNotaDebitoMapper) {
        this.impuestoNotaDebitoMapper = impuestoNotaDebitoMapper;
    }

}