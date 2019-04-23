/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rolandopalermo.facturacion.ec.modelo.notacredito;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.rolandopalermo.facturacion.ec.modelo.Comprobante;

import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

/**
 * @author Rolando
 */
@Getter
@Setter
@XmlRootElement(name = "notaCredito")
@XmlType(propOrder = { 
		"id", 
		"version", 
		"infoTributaria", 
		"infoNotaCredito", 
		"detalle", 
		"campoAdicional" 
		})
public class NotaCredito extends Comprobante {

	private InfoNotaCredito infoNotaCredito;
	@Singular("detalle")
	private List<Detalle> detalle;
	
	@XmlElementWrapper(name = "detalles")
	public List<Detalle> getDetalle() {
		return detalle;
	}
	
}