package com.rolandopalermo.facturacion.ec.common.util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.rolandopalermo.facturacion.ec.common.exception.VeronicaException;

public class XmlUtils {

	private static final Logger logger = LogManager.getLogger(XmlUtils.class);

	private static final XPathFactory X_PATH_FACTORY = XPathFactory.newInstance();

	public static Document convertStringToDocument(String xmlStr) throws VeronicaException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
			return doc;
		} catch (Exception e) {
			logger.error("convertStringToDocument", e);
			throw new VeronicaException(e.getMessage());
		}
	}

	public static String xPath(Document document, String xpathExpression) throws Exception {
		XPath xPath = X_PATH_FACTORY.newXPath();
		Node node = (Node) xPath.compile(xpathExpression).evaluate(document, XPathConstants.NODE);
		return node.getTextContent();
	}

	public static String getXmlRootElement(Document doc) {
		Element root = doc.getDocumentElement();
		return root.getNodeName().toString();
	}

}