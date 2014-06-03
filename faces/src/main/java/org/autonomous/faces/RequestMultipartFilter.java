package org.autonomous.faces;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Com esse filtro é possível obter arquivos enviados pelo Request a partir da
 * tag {@code <input type="file" />}.
 * 
 * A versão 2.1 do JSF não oferece suporte nativo a upload de arquivos externos,
 * com isso se faz necessário utilizar o framework commons-fileUpload da Apache
 * {@link http://commons.apache.org/proper/commons-fileupload/} e dessa classe
 * para conseguir obter um request do tipo multipart
 * {@code <h:form enctype="multipart/form-data">} e ler os respectivos arquivos
 * enviados com ele.
 * 
 * @author arthemus
 * @since 21/05/2014
 * 
 */
public class RequestMultipartFilter implements Filter {

	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	protected FilterConfig filterConfig;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	@Override
	public void destroy() {
		this.filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		boolean isMultipartContent = ServletFileUpload.isMultipartContent(httpRequest);
		if (!isMultipartContent) {
			chain.doFilter(request, response);
			return;
		}

		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(THRESHOLD_SIZE);
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(MAX_FILE_SIZE);
			upload.setSizeMax(REQUEST_SIZE);
			upload.setHeaderEncoding("UTF-8");

			List<FileItem> items = upload.parseRequest(httpRequest);
			final Map<String, String[]> parameterMap = new HashMap<String, String[]>();

			for (FileItem item : items) {
				if (item.isFormField()) {
					processFormField(item, parameterMap);
				} else {
					processFileField(item, httpRequest);
				}
			}

			chain.doFilter(new HttpServletRequestWrapper(httpRequest) {

				@Override
				public Map<String, String[]> getParameterMap() {
					return parameterMap;
				}

				@Override
				public String[] getParameterValues(String name) {
					return parameterMap.get(name);
				}

				@Override
				public String getParameter(String name) {
					String[] params = getParameterValues(name);
					if (params == null) {
						return null;
					}
					return params[0];
				}

				@Override
				public Enumeration<String> getParameterNames() {
					return Collections.enumeration(parameterMap.keySet());
				}
			}, response);

		} catch (Exception ex) {
			ServletException servletException = new ServletException();
			servletException.initCause(ex);
			throw servletException;
		}
	}

	/**
	 * Processa apenas componentes da tela, não arquivos enviados.
	 * 
	 * @param formField
	 * @param parameterMap
	 */
	private void processFormField(FileItem formField,
			Map<String, String[]> parameterMap) {
		String name = formField.getFieldName();
		String value = formField.getString();
		String[] values = parameterMap.get(name);
		if (values == null) {
			parameterMap.put(name, new String[] { value });
		} else {
			int length = values.length;
			String[] newValues = new String[length + 1];
			System.arraycopy(values, 0, newValues, 0, length);
			newValues[length] = value;
			parameterMap.put(name, newValues);
		}
	}

	/**
	 * Processa arquivo enviados pelo upload e os adiciona na cadeia de
	 * atributos do request.
	 * 
	 * @param fileField
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	private void processFileField(FileItem fileField, HttpServletRequest request) {
		List<FileItem> fileFields = null;
		if (request.getAttribute(fileField.getFieldName()) == null) {
			fileFields = new ArrayList<FileItem>(0);
			fileFields.add(fileField);
		} else {
			fileFields = (List<FileItem>) 
					request.getAttribute(fileField.getFieldName());
			fileFields.add(fileField);
		}
		request.setAttribute(fileField.getFieldName(), fileFields);
	}

}
