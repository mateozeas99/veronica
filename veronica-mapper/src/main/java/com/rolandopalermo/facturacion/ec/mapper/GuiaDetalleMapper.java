package com.rolandopalermo.facturacion.ec.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rolandopalermo.facturacion.ec.dto.v1_0.DetAdicionalDTO;
import com.rolandopalermo.facturacion.ec.dto.v1_0.bol.GuiaDetallesDTO;
import com.rolandopalermo.facturacion.ec.modelo.DetAdicional;
import com.rolandopalermo.facturacion.ec.modelo.guia.GuiaDetalles;

@Component("guiaDetalleMapper")
public class GuiaDetalleMapper implements Mapper<GuiaDetallesDTO, GuiaDetalles> {

    private Mapper<DetAdicionalDTO, DetAdicional> detAdicionalMapper;

    @Override
    public GuiaDetalles convert(final GuiaDetallesDTO guiaDetallesDTO) {
        if (guiaDetallesDTO == null) {
            return null;
        }
        final GuiaDetalles detalle = new GuiaDetalles();
        detalle.setCodigoInterno(guiaDetallesDTO.getCodigoInterno());
        detalle.setCodigoAdicional(guiaDetallesDTO.getCodigoAdicional());
        detalle.setDescripcion(guiaDetallesDTO.getDescripcion());
        detalle.setCantidad(guiaDetallesDTO.getCantidad());
        detalle.setDetAdicional(getDetAdicionalMapper().convertAll(guiaDetallesDTO.getDetAdicional()));
        return detalle;
    }

    protected Mapper<DetAdicionalDTO, DetAdicional> getDetAdicionalMapper() {
        return detAdicionalMapper;
    }

    @Autowired
    @Qualifier("detAdicionalMapper")
    public void setDetAdicionalMapper(Mapper<DetAdicionalDTO, DetAdicional> detAdicionalMapper) {
        this.detAdicionalMapper = detAdicionalMapper;
    }
    
}