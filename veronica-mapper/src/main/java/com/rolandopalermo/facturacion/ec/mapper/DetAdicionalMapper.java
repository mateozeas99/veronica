package com.rolandopalermo.facturacion.ec.mapper;

import org.springframework.stereotype.Component;

import com.rolandopalermo.facturacion.ec.dto.v1_0.DetAdicionalDTO;
import com.rolandopalermo.facturacion.ec.modelo.DetAdicional;

@Component("detAdicionalMapper")
public class DetAdicionalMapper implements Mapper<DetAdicionalDTO, DetAdicional> {
	
    @Override
    public DetAdicional convert(final DetAdicionalDTO detAdicionalDTO) {
        if (detAdicionalDTO == null) {
            return null;
        }
        final DetAdicional detAdicional = new DetAdicional();
        detAdicional.setNombre(detAdicionalDTO.getNombre());
        detAdicional.setValor(detAdicionalDTO.getValor());
        return detAdicional;
    }
    
}