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
 * Classe para impressão de conteudo em projetos Web.
 * 
 * @author arthemus
 * @since 25/06/2013
 */
public class WebReport {

	private static FacesContext context;
	private static HttpServletResponse response;
	private static ServletOutputStream servletOutputStream;
	private static InputStream inputStream;

	/**
	 * Prepara o Input e Output Stream de impressão.
	 * 
	 * @param classReference
	 * @param fileReport
	 * @throws IOException
	 */
	private static void prepareStream(Class<?> classReference, String fileReport) throws IOException {
		servletOutputStream = response.getOutputStream();
		inputStream = classReference.getResourceAsStream(fileReport);
	}

	/**
	 * Declara o arquivo a ser exportado.
	 * 
	 * @param pdfName
	 */
	private static void declareFilePdf(String pdfName) {
		response.setHeader("Content-Disposition", "inline; filename=".concat(pdfName).concat(".pdf"));
		response.setContentType("application/pdf");
	}
	
	/**
	 * Exportar relatório no formato Pdf.
	 * 
	 * @param classReference
	 * @param fileReport
	 * @param pdfName
	 * @param parameters
	 * @throws JRException
	 * @throws IOException
	 */
	public static void printPdf(Class<?> classReference, String fileReport,
			String pdfName, Map<String, Object> parameters) 
					throws JRException, IOException {
		
		context = FacesContext.getCurrentInstance();
		response = (HttpServletResponse) context.getExternalContext().getResponse();
		
		try {
			declareFilePdf(pdfName);
			prepareStream(classReference, fileReport);

			JasperRunManager.runReportToPdfStream(inputStream, 
					servletOutputStream, parameters);			

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			throw new JRException("Problemas para imprimir o relatório!\nErro: " + e.getMessage());
		} catch (IOException e) {
			throw new IOException("O arquivo [" + fileReport + "] não foi encontrado!\nErro: " + e.getMessage());
		} finally {
			context.renderResponse();
			context.responseComplete();
		}
	}
	
	/**
	 * Exportar relatório no formato Pdf.
	 * 
	 * @param classReference
	 * @param fileReport
	 * @param pdfName
	 * @param parameters
	 * @param collectionDataSource
	 * @throws JRException
	 * @throws IOException
	 */
	public static void printPdf(Class<?> classReference, String fileReport,
			String pdfName, Map<String, Object> parameters,
			Collection<?> collectionDataSource) throws JRException, IOException {

		context = FacesContext.getCurrentInstance();
		response = (HttpServletResponse) context.getExternalContext().getResponse();
		
		try {
			declareFilePdf(pdfName);
			prepareStream(classReference, fileReport);

			JRDataSource jrDataSource = new JRBeanCollectionDataSource(collectionDataSource);
			JasperRunManager.runReportToPdfStream(inputStream, 
					servletOutputStream, parameters, jrDataSource);

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			throw new JRException("Problemas para imprimir o relatório!\nErro: " + e.getMessage());
		} catch (IOException e) {
			throw new IOException("O arquivo [" + fileReport + "] não foi encontrado!\nErro: " + e.getMessage());
		} finally {
			context.renderResponse();
			context.responseComplete();
		}
	}

	/**
	 * Exportar relatório no formato Pdf.
	 * 
	 * @param classReference
	 * @param fileReport
	 * @param pdfName
	 * @param parameters
	 * @param conexao
	 * @throws JRException
	 * @throws IOException
	 */
	public static void printPdf(Class<?> classReference, String fileReport,
			String pdfName, Map<String, Object> parameters, Connection conexao) 
					throws JRException, IOException {
		
		context = FacesContext.getCurrentInstance();
		response = (HttpServletResponse) context.getExternalContext().getResponse();
		
		try {
			declareFilePdf(pdfName);
			prepareStream(classReference, fileReport);

			JasperRunManager.runReportToPdfStream(inputStream, 
					servletOutputStream, parameters, conexao);			

			servletOutputStream.flush();
			servletOutputStream.close();

		} catch (JRException e) {
			throw new JRException("Problemas para imprimir o relatório!\nErro: " + e.getMessage());
		} catch (IOException e) {
			throw new IOException("O arquivo [" + fileReport + "] não foi encontrado!\nErro: " + e.getMessage());
		} finally {
			context.renderResponse();
			context.responseComplete();
		}
	}
		
}
