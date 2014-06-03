package org.autonomous.functions;

import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * Para extrair o conteudo de um arquivo PDF.
 * 
 * @author arthemus
 * @since 03/09/2013
 */
public class PDFExtractor {

	/**
	 * Obtem o conteudo do arquivo PDF no formato texto.
	 * 
	 * @param file
	 * @return
	 */
	public String getTexto(InputStream file) {
		String textoExtraido = null;
		PDDocument pdDoc = null;
		try {
			pdDoc = PDDocument.load(file);
			PDFTextStripper stripper = new PDFTextStripper();
			textoExtraido = stripper.getText(pdDoc);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {				
				if (pdDoc != null) pdDoc.close();
			} catch (IOException e) {				
			}
		}
		return textoExtraido;
	}

	/**
	 * Obtem o total de paginas do arquivo.
	 * 
	 * @param file
	 * @return
	 */
	public int getTotalPaginas(InputStream file) {
		int numPaginas = 0;
		PDDocument pdDoc = null;
		try {
			pdDoc = PDDocument.load(file);
			numPaginas = pdDoc.getNumberOfPages();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pdDoc != null) pdDoc.close();
			} catch (IOException e) {			
			}
		}
		return numPaginas;
	}

	/**
	 * Obtem o conteudo do arquivo dentro de um intervalo de páginas.
	 * 
	 * @param file
	 * @param paginaInicio
	 * @param paginaFinal
	 * @return
	 */
	public String getTexto(InputStream file, int paginaInicio, int paginaFinal) {
		String textoExtraido = null;		
		PDDocument pdDoc = null;
		try {
			pdDoc = PDDocument.load(file);
			PDFTextStripper stripper = new PDFTextStripper();
			stripper.setStartPage(paginaInicio);
			stripper.setEndPage(paginaFinal);
			textoExtraido = stripper.getText(pdDoc);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pdDoc != null) pdDoc.close();
			} catch (IOException e) {			
			}
		}
		return textoExtraido;
	}
}
