package com.rolandopalermo.facturacion.ec.common.converter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;

public class JaxbConverter {

	private Marshaller marshaller;
	private Unmarshaller unmarshaller;

	public Marshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public Unmarshaller getUnmarshaller() {
		return unmarshaller;
	}

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}

	public void convertFromObjectToXML(Object object, String filepath) throws IOException, XmlMappingException {
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(filepath);
			getMarshaller().marshal(object, new StreamResult(os));
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}
	
	public String convertFromObjectToString(Object object) throws IOException, XmlMappingException {
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		getMarshaller().marshal(object, result);
		return writer.toString();
	}

	public Object convertFromXMLToObject(String xmlContent) throws IOException {
		return getUnmarshaller().unmarshal(new StreamSource(new StringReader(xmlContent)));
	}
}