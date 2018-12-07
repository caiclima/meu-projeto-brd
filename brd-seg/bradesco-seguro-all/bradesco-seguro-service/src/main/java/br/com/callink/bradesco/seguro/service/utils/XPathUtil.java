package br.com.callink.bradesco.seguro.service.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XPathUtil {
	
	private DocumentBuilder documentBuilder;
	
	private XPath xPath;
	
	private Document document;
	
	public XPathUtil(String xml) throws Exception {
		try {
			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			xPath = XPathFactory.newInstance().newXPath();
			document = documentBuilder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			throw new Exception("Message: "+e.getMessage()+" - Cause: "+e.getCause());
		} catch (SAXException e) {
			throw new Exception("Message: "+e.getMessage()+" - Cause: "+e.getCause());
		} catch (IOException e) {
			throw new Exception("Message: "+e.getMessage()+" - Cause: "+e.getCause());
		} catch (ParserConfigurationException e) {
			throw new Exception("Message: "+e.getMessage()+" - Cause: "+e.getCause());
		}
	}
	
	public Node getNode(String expression) throws XPathExpressionException {
		return (Node) xPath.compile(expression).evaluate(document, XPathConstants.NODE);
	}
	
	
	public NodeList getNodeList(String expression) throws XPathExpressionException {
		return (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
	}
	
	/**
	 * =========== GETTERS/SETTERS ===========
	 */
	
	public DocumentBuilder getDocumentBuilder() {
		return documentBuilder;
	}

	public void setDocumentBuilder(DocumentBuilder documentBuilder) {
		this.documentBuilder = documentBuilder;
	}

	public XPath getxPath() {
		return xPath;
	}

	public void setxPath(XPath xPath) {
		this.xPath = xPath;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
}
