package org.autonomous.functions.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Utility class for working with XML files.
 *
 * @author Arthemus C. Moreira
 * @since 28/05/2013
 */
public class Xml {

	private final Document document;

	public Xml(final Document document) {
		this.document = document;
	}

	public Xml(final InputStream file) throws ParserConfigurationException,
			SAXException, IOException {
		this.document = buildDocument(file);
	}

	/**
	 * Obtains an instance of the XML document.
	 *
	 * @return The XML Document object.
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * Reads a given XML file.
	 *
	 * @param file
	 *            The file to be read.
	 * @return A new Document with the values from the file.
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	private Document buildDocument(InputStream file)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new ParserConfigurationException("Problems creating new document!"
					+ "\nError: " + e.getMessage());
		}
		Document doc = null;
		try {
			doc = db.parse(file);
		} catch (SAXException e) {
			throw new SAXException("Problems reading XML file!"
					+ "\nError: " + e.getMessage());
		} catch (IOException e) {
			throw new IOException("XML file not found!"
					+ "\nError: " + e.getMessage());
		}
		return doc;
	}

	/**
	 * Obtains the value of a node from the XML file.
	 *
	 * @param tagName
	 *            The name of the tag to retrieve.
	 * @return The value of the tag as a string.
	 */
	public String getTag(final String tagName) {
		String value = new String();
		Element root = document.getDocumentElement();
		NodeList parentNode = root.getElementsByTagName(tagName);
		if (parentNode == null) return value;
		Element parentItem = (Element) parentNode.item(0);
		if (parentItem == null) return value;
		Node childNode = parentItem.getFirstChild();
		value = childNode.getNodeValue();
		return value;
	}

	/**
	 * Saves the content of the 'Document' attribute to an external file.
	 *
	 * @param file
	 *            The destination file.
	 * @throws TransformerException
	 * @throws IOException
	 */
	public void save(File file) throws TransformerException, IOException {
		Transformer transformer = null;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException e) {
			throw new TransformerConfigurationException("Problems with file configuration!"
					+ "\nError: " + e.getMessage());
		} catch (TransformerFactoryConfigurationError e) {
			throw new TransformerFactoryConfigurationError(
					"Problems creating file configuration!" + "\nError: "
							+ e.getMessage());
		}

		/*
		 * If set to "no", writes the file content on a single line.
		 * If "yes", formats (indents) the file content.
		 */
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		StreamResult result = new StreamResult(new StringWriter());

		DOMSource source = new DOMSource(document);
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			throw new TransformerException(
					"Problems transforming XML file content to text!"
							+ "\nError: " + e.getMessage());
		}

		String xmlContent = result.getWriter().toString();
		try {
			FileWriter fileWrite = new FileWriter(file);
			fileWrite.write(xmlContent);
			fileWrite.flush();
			fileWrite.close();
		} catch (IOException e) {
			throw new IOException("Problems creating XML file!"
					+ "\nError: " + e.getMessage());
		}
	}
}
