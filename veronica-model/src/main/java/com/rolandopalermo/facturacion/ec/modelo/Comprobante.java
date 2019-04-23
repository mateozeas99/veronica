package com.rolandopalermo.facturacion.ec.modelo;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlTransient
public class Comprobante {

    protected String id;
    protected String version;
    protected InfoTributaria infoTributaria;
    protected List<CampoAdicional> campoAdicional;

    @XmlAttribute(name = "id")
    public String getId() {
        return id;
    }

    @XmlAttribute(name = "version")
    public String getVersion() {
        return version;
    }

    @XmlElementWrapper(name = "infoAdicional")
    public List<CampoAdicional> getCampoAdicional() {
        return campoAdicional;
    }

}
