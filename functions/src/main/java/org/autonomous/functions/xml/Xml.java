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
 * Classe utilitária para trabalhar com arquivos XML.
 * 
 * @author Arthemus C. Moreira
 * @since 28/05/2013
 */
public class Xml {

	private final Document document;

	public Xml(final Document documento) {
		this.document = documento;
	}
	
	public Xml(final InputStream arquivo) throws ParserConfigurationException, 
			SAXException, IOException {
		this.document = buildDocument(arquivo);		
	}
	
	/**
	 * Obtem uma instancia do documento Xml.
	 * 
	 * @return
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * Realiza a leitura de um determinado arquivo XML.
	 * 
	 * @param arquivo
	 *            Arquivo a ser lido.
	 * @return Novo Documento com os valores do arquivo.
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws FacadeException
	 *             Excecoes decorrentes.
	 */
	private Document buildDocument(InputStream arquivo) 
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new ParserConfigurationException("Problemas ao criar novo document!"
					+ "\nErro: " + e.getMessage());
		}
		Document doc = null;		
		try {
			doc = db.parse(arquivo);
		} catch (SAXException e) {
			throw new SAXException("Problemas ao ler arquivo XML!"
					+ "\nErro: " + e.getMessage());
		} catch (IOException e) {
			throw new IOException("Arquivo XML não encontrado!"
					+ "\nErro: " + e.getMessage());
		}		
		return doc;
	}

	/**
	 * Obtem o valor de um nó do arquivo Xml.
	 * 
	 * @param tagName
	 * @return
	 */
	public String getTag(final String tagName) {		
		String valor = new String();
		Element raiz = document.getDocumentElement();
		NodeList noPai = raiz.getElementsByTagName(tagName);
		if (noPai == null) return valor;
		Element itemPai = (Element) noPai.item(0);
		if (itemPai == null) return valor;
		Node noFilho = itemPai.getFirstChild();
		valor = noFilho.getNodeValue();
		return valor;
	}

	/**
	 * Salva o conteudo do atributo 'Document' em um arquivo externo.
	 * 
	 * @param file
	 * @throws TransformerException
	 * @throws IOException
	 */
	public void save(File file) throws TransformerException, IOException {
		Transformer transformer = null;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException e) {
			throw new TransformerConfigurationException("Problemas na configuração do arquivo!"
					+ "\nErro: " + e.getMessage());
		} catch (TransformerFactoryConfigurationError e) {
			throw new TransformerFactoryConfigurationError(
					"Problemas ao criar configuração do arquivo!" + "\nErro: "
							+ e.getMessage());
		}

		/*
		 * Se passado como parametro, "no", escreve o conteudo do arquivo em uma
		 * unica linha. Se "yes", formata (Indenta) o conteudo do arquivo.
		 */
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		StreamResult result = new StreamResult(new StringWriter());
		
		DOMSource fonte = new DOMSource(document);
		try {
			transformer.transform(fonte, result);
		} catch (TransformerException e) {
			throw new TransformerException(
					"Problemas ao transformar o conteudo do arquivo XML em Texto!"
							+ "\nErro: " + e.getMessage());
		}

		String conteudoXml = result.getWriter().toString();
		try {
			FileWriter fileWrite = new FileWriter(file);
			fileWrite.write(conteudoXml);
			fileWrite.flush();
			fileWrite.close();
		} catch (IOException e) {
			throw new IOException("Problemas ao criar arquivo XML!"
					+ "\nErro: " + e.getMessage());
		}
	}
}
