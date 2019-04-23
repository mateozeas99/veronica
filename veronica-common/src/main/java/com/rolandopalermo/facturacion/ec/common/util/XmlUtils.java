package com.rolandopalermo.facturacion.ec.common.util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;

public class XmlUtils {

	private static final Logger logger = LogManager.getLogger(XmlUtils.class);

	public static Document convertStringToDocument(String xmlStr) throws VeronicaException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
			return doc;
		} catch (Exception e) {
			logger.error("XmlUtils", e);
			throw new VeronicaException(e.getMessage());
		}
	}

	public static String getXmlRootElement(String xmlContent) {
		try {
			Document doc = convertStringToDocument(xmlContent);
			Element root = doc.getDocumentElement();
			return root.getNodeName();
		} catch (VeronicaException e) {
			return "";
		}
	}

}