package org.autonomous.functions;

import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * For extracting the content of a PDF file.
 *
 * @author arthemus
 * @since 03/09/2013
 */
public class PDFExtractor {

	private static final Logger LOGGER = LoggerFactory.getLogger(PDFExtractor.class);

	/**
	 * Obtains the content of the PDF file in text format.
	 *
	 * @param file
	 *            The input stream of the PDF file.
	 * @return The extracted text content.
	 */
	public String getText(InputStream file) {
		String extractedText = null;
		PDDocument pdDoc = null;
		try {
			pdDoc = PDDocument.load(file);
			PDFTextStripper stripper = new PDFTextStripper();
			extractedText = stripper.getText(pdDoc);
		} catch (IOException e) {
			LOGGER.error("Failed to extract text from PDF", e);
		} finally {
			try {
				if (pdDoc != null) pdDoc.close();
			} catch (IOException e) {
				LOGGER.error("Failed to close PDF document", e);
			}
		}
		return extractedText;
	}

	/**
	 * Obtains the total number of pages in the file.
	 *
	 * @param file
	 *            The input stream of the PDF file.
	 * @return The total number of pages.
	 */
	public int getTotalPages(InputStream file) {
		int pageCount = 0;
		PDDocument pdDoc = null;
		try {
			pdDoc = PDDocument.load(file);
			pageCount = pdDoc.getNumberOfPages();
		} catch (IOException e) {
			LOGGER.error("Failed to get page count from PDF", e);
		} finally {
			try {
				if (pdDoc != null) pdDoc.close();
			} catch (IOException e) {
				LOGGER.error("Failed to close PDF document", e);
			}
		}
		return pageCount;
	}

	/**
	 * Obtains the content of the file within a range of pages.
	 *
	 * @param file
	 *            The input stream of the PDF file.
	 * @param startPage
	 *            The starting page number.
	 * @param endPage
	 *            The ending page number.
	 * @return The extracted text content.
	 */
	public String getText(InputStream file, int startPage, int endPage) {
		String extractedText = null;
		PDDocument pdDoc = null;
		try {
			pdDoc = PDDocument.load(file);
			PDFTextStripper stripper = new PDFTextStripper();
			stripper.setStartPage(startPage);
			stripper.setEndPage(endPage);
			extractedText = stripper.getText(pdDoc);
		} catch (IOException e) {
			LOGGER.error("Failed to extract text from PDF page range", e);
		} finally {
			try {
				if (pdDoc != null) pdDoc.close();
			} catch (IOException e) {
				LOGGER.error("Failed to close PDF document", e);
			}
		}
		return extractedText;
	}
}
