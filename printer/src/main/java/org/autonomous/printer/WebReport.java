package org.autonomous.printer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Renders JasperReports content to PDF in web projects.
 *
 * @author arthemus
 * @since 25/06/2013
 */
public class WebReport {

	private final FacesContext context;
	private final HttpServletResponse response;

	public WebReport(FacesContext context, HttpServletResponse response) {
		this.context = context;
		this.response = response;
	}

	public WebReport(FacesContext context) {
		this(context, (HttpServletResponse) context.getExternalContext().getResponse());
	}

	/**
	 * Prepares the input and output streams for printing.
	 *
	 * @param classReference
	 * @param fileReport
	 * @throws IOException
	 */
	private InputStream prepareStream(Class<?> classReference, String fileReport) throws IOException {
		return classReference.getResourceAsStream(fileReport);
	}

	/**
	 * Declares the file to be exported as a PDF response.
	 *
	 * @param pdfName
	 */
	private void declarePdfFile(String pdfName) {
		response.setHeader("Content-Disposition", "inline; filename=".concat(pdfName).concat(".pdf"));
		response.setContentType("application/pdf");
	}

	/**
	 * Exports a report to PDF format using only parameters.
	 *
	 * @param classReference
	 * @param fileReport
	 * @param pdfName
	 * @param parameters
	 * @throws JRException
	 * @throws IOException
	 */
	public void printPdf(Class<?> classReference, String fileReport,
			String pdfName, Map<String, Object> parameters)
					throws JRException, IOException {
		InputStream stream = prepareStream(classReference, fileReport);
		declarePdfFile(pdfName);
		ServletOutputStream outputStream = response.getOutputStream();
		try {
			JasperRunManager.runReportToPdfStream(stream, outputStream, parameters);
		} catch (JRException e) {
			throw new JRException("Failed to print report!\nError: " + e.getMessage());
		} finally {
			flushAndClose(outputStream);
		}
	}

	/**
	 * Exports a report to PDF format using parameters and a collection data source.
	 *
	 * @param classReference
	 * @param fileReport
	 * @param pdfName
	 * @param parameters
	 * @param collectionDataSource
	 * @throws JRException
	 * @throws IOException
	 */
	public void printPdf(Class<?> classReference, String fileReport,
			String pdfName, Map<String, Object> parameters,
			Collection<?> collectionDataSource) throws JRException, IOException {
		InputStream stream = prepareStream(classReference, fileReport);
		declarePdfFile(pdfName);
		JRDataSource jrDataSource = new JRBeanCollectionDataSource(collectionDataSource);
		ServletOutputStream outputStream = response.getOutputStream();
		try {
			JasperRunManager.runReportToPdfStream(stream, outputStream, parameters, jrDataSource);
		} catch (JRException e) {
			throw new JRException("Failed to print report!\nError: " + e.getMessage());
		} finally {
			flushAndClose(outputStream);
		}
	}

	/**
	 * Exports a report to PDF format using parameters and a JDBC connection.
	 *
	 * @param classReference
	 * @param fileReport
	 * @param pdfName
	 * @param parameters
	 * @param connection
	 * @throws JRException
	 * @throws IOException
	 */
	public void printPdf(Class<?> classReference, String fileReport,
			String pdfName, Map<String, Object> parameters, Connection connection)
					throws JRException, IOException {
		InputStream stream = prepareStream(classReference, fileReport);
		declarePdfFile(pdfName);
		ServletOutputStream outputStream = response.getOutputStream();
		try {
			JasperRunManager.runReportToPdfStream(stream, outputStream, parameters, connection);
		} catch (JRException e) {
			throw new JRException("Failed to print report!\nError: " + e.getMessage());
		} finally {
			flushAndClose(outputStream);
		}
	}

	private void flushAndClose(ServletOutputStream outputStream) throws IOException {
		try {
			outputStream.flush();
		} finally {
			outputStream.close();
		}
		if (context != null) {
			context.responseComplete();
		}
	}
}
