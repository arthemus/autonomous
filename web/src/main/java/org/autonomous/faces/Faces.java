package org.autonomous.faces;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Class to centralize common utilities for working with JSF.
 *
 * @author arthemus
 * @since 26/09/2012
 */
public final class Faces {

	/**
	 * Lazily-computed root directory of the application. The value is resolved
	 * on first access through {@link #getFacesContextPath()} so that this class
	 * can be loaded outside a JSF request without throwing
	 * {@link ExceptionInInitializerError}.
	 */
	private static String facesContextPath;

	private static final String FILE_DOWNLOAD = "attachment";

	private static final String FILE_OPEN = "inline";

	/**
	 * Adds an error message to the application context to be displayed on a
	 * given screen component.
	 *
	 * @author arthemus
	 * @param message
	 *            Message to be displayed.
	 * @param target
	 *            Target form.
	 */
	public static final void addMessageError(String message, String target) {
		throwMessageContext(message, target, "Error", FacesMessage.SEVERITY_ERROR);
	}

	/**
	 * Adds an error message to the application context containing a given
	 * message, summary and target component.
	 *
	 * @author arthemus
	 * @param message
	 *            Message to be displayed.
	 * @param target
	 *            Target form.
	 * @param detail
	 *            Message summary, reference.
	 */
	public static final void addMessageError(String message, String target, String detail) {
		throwMessageContext(message, target, detail, FacesMessage.SEVERITY_ERROR);
	}

	/**
	 * Adds an information message to the application context to be displayed on
	 * a given screen component.
	 *
	 * @author arthemus
	 * @param message
	 *            Message to be displayed.
	 * @param target
	 *            Target component.
	 */
	public static final void addMessageInfo(String message, String target) {
		throwMessageContext(message, target, "Information", FacesMessage.SEVERITY_INFO);
	}

	/**
	 * Adds an information message to the application context containing a given
	 * message, summary and target component.
	 *
	 * @author arthemus
	 * @param message
	 *            Message to be displayed.
	 * @param target
	 *            Target form.
	 * @param detail
	 *            Message summary, reference.
	 */
	public static final void addMessageInfo(String message, String target, String detail) {
		throwMessageContext(message, target, detail, FacesMessage.SEVERITY_INFO);
	}

	/**
	 * Downloads a given file based on the current Request and Response of the
	 * FacesContext.
	 *
	 * Exclusive for JSF pages.
	 *
	 * @param file
	 * @throws IOException
	 */
	public static final void doDownloader(File file) throws IOException {
		HttpServletResponse response = (HttpServletResponse)
				FacesContext.getCurrentInstance().getExternalContext().getResponse();

		HttpServletRequest request = (HttpServletRequest)
				FacesContext.getCurrentInstance().getExternalContext().getRequest();

		Faces.doDownloader(request, response, file);

		FacesContext.getCurrentInstance().responseComplete();
	}

	/**
	 * Opens a given file directly in the browser. The browser must have native
	 * support for the file type, otherwise the file will be sent to the browser
	 * as a download.
	 *
	 * @param file
	 * @throws IOException
	 */
	public static final void doOpen(File file) throws IOException {
		HttpServletResponse response = (HttpServletResponse)
				FacesContext.getCurrentInstance().getExternalContext().getResponse();

		HttpServletRequest request = (HttpServletRequest)
				FacesContext.getCurrentInstance().getExternalContext().getRequest();

		doOperationFile(request, response, file, FILE_OPEN);

		FacesContext.getCurrentInstance().responseComplete();
	}

	/**
	 * Downloads a given file.
	 *
	 * Works for both JSF pages and JSP pages using Servlets.
	 *
	 * @param request
	 * @param response
	 * @param file
	 * @throws IOException
	 */
	public static final void doDownloader(HttpServletRequest request,
			HttpServletResponse response, File file) throws IOException {
		doOperationFile(request, response, file, FILE_DOWNLOAD);
	}

	/**
	 * Performs a given operation with a file.
	 *
	 * @param request
	 * @param response
	 * @param file
	 * @param contentDisposition
	 *            Operation, either a download or a direct opening.
	 * @throws IOException
	 */
	private static final void doOperationFile(HttpServletRequest request,
			HttpServletResponse response, File file, String contentDisposition)
					throws IOException {
		try {
			response.setHeader("Content-Disposition",
					contentDisposition + "; filename=\"" + file.getName() + "\"");
			response.setContentLength((int) file.length());

			FileInputStream input = new FileInputStream(file);
			ServletOutputStream out = response.getOutputStream();

			byte[] buffer = new byte[Byte.MAX_VALUE];
			int i;

			try {
				while ((i = input.read(buffer)) != -1)
					out.write(buffer, 0, i);
			} finally {
				out.flush();
				out.close();
			}

			input.close();

		} catch (Exception e) {
			throw new IOException("Error processing file: " + e.getMessage());
		}
	}

	/**
	 * Obtains the current external context.
	 *
	 * @return
	 */
	private static ExternalContext externalContext() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		return external;
	}

	/**
	 * Obtains the current application context.
	 *
	 * @author arthemus
	 * @return Current context.
	 */
	public static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	/**
	 * Obtains the login of the logged-in user.
	 *
	 * @author arthemus
	 * @return Login.
	 */
	public static String getLoginSession() {
		ExternalContext external = externalContext();
		return external.getRemoteUser().trim();
	}

	/**
	 * Obtains a given object from the current session.
	 *
	 * @author arthemus
	 * @param objectName
	 *            Name of the desired object.
	 * @return Session object.
	 */
	public static Object getObjectSession(String objectName) {
		ExternalContext external = externalContext();
		HttpSession session = (HttpSession) external.getSession(true);
		return session.getAttribute(objectName);
	}

	/**
	 * Obtains the application context.
	 *
	 * @author arthemus
	 * @return Root context.
	 */
	private static String getRealPath() {
		FacesContext aFacesContext = FacesContext.getCurrentInstance();
		ServletContext context = (ServletContext) aFacesContext.getExternalContext().getContext();
		String realPath = context.getRealPath("/");
		return realPath;
	}

	/**
	 * Lazily resolves and returns the root directory of the application. The
	 * value is computed on first access and cached, allowing this class to be
	 * loaded outside a JSF request without throwing
	 * {@link ExceptionInInitializerError}.
	 *
	 * @author arthemus
	 * @return Root context path.
	 */
	public static String getFacesContextPath() {
		if (facesContextPath == null) {
			facesContextPath = Faces.getRealPath();
		}
		return facesContextPath;
	}

	/**
	 * Obtains the current Http session.
	 *
	 * @author arthemus
	 * @return Current session.
	 */
	public static HttpSession getSession() {
		ExternalContext external = externalContext();
		HttpSession session = (HttpSession) external.getSession(true);
		return session;
	}

	/**
	 * Routes the application to a given point. Useful to obtain the exact
	 * location of external files in specific directories of the system.
	 *
	 * @author arthemus
	 * @param path
	 *            Target directory or file.
	 * @return Full path from the application root to the specified target.
	 */
	public static String goTo(String path) {
		String contextPath = Faces.getFacesContextPath();
		StringBuilder str = new StringBuilder(contextPath.length() * 2);
		str.append(contextPath);
		str.append("WEB-INF");
		str.append("/");
		str.append(path);
		return str.toString();
	}

	/**
	 * Stores a given object in the current session.
	 *
	 * @author arthemus
	 * @param name
	 *            Name of the object for later lookup.
	 * @param value
	 *            Object in question.
	 */
	public static final void saveInSession(final String name, final Object value) {
		ExternalContext external = externalContext();
		HttpSession session = (HttpSession) external.getSession(true);
		session.setAttribute(name, value);
	}

	/**
	 * Raises a message in the current application context.
	 *
	 * @param message
	 *            Message to be sent.
	 * @param target
	 *            ID of the component to receive the message.
	 * @param detail
	 *            Message detail.
	 * @param severity
	 *            Type: Information, Error, Warning...
	 */
	private static final void throwMessageContext(String message, String target, String detail, Severity severity) {
		FacesMessage faceMsg = new FacesMessage();
		faceMsg.setSeverity(severity);
		faceMsg.setSummary(detail);
		faceMsg.setDetail(message);
		FacesContext.getCurrentInstance().addMessage(target, faceMsg);
	}

	/**
	 * Obtains the name of the file provided for upload.
	 *
	 * @param file
	 *            Usually a file provided in an upload field.
	 * @return File name.
	 */
	public static String getName(final Part file) {
		String[] content = file.getHeader("content-disposition").split(";");
		for (String part : content) {
			if (part.trim().startsWith("filename")) {
				String filename = part.substring(part.indexOf("=") + 1).trim().replace("\"", "");
				int lastSlash = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));
				return lastSlash >= 0 ? filename.substring(lastSlash + 1) : filename;
			}
		}
		return new String("[Could not obtain the file name]");
	}

	/**
	 * Obtains the content of the file. Works best for text-format files such as
	 * xml, json or txt. For image, document or pdf files, the return may
	 * contain several special characters (machine language).
	 *
	 * @param file
	 *            Usually a file provided in a RequestUpload field.
	 * @return File content in text format.
	 * @throws IOException
	 *             If the file cannot be read.
	 */
	public static String getContent(final Part file) throws IOException {
		try {
			return new Scanner(file.getInputStream()).useDelimiter("\\A").next();
		} catch (IOException e) {
			throw new IOException("Could not read the file.\nError: " + e.getMessage());
		}
	}

	/**
	 * Obtains the IP number of the client making the current request.
	 *
	 * @return
	 */
	public static String getClientIP() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request.getRemoteAddr().trim();
	}

	/**
	 * i18n
	 *
	 * Obtains a message from the internationalization files.
	 *
	 * @deprecated Use the {@code Messages} interface with the {@code FacesMessages} implementation.
	 * @param property
	 * @return
	 */
	@Deprecated
	public static String getMessage(String property) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		return bundle.getString(property);
	}

	/**
	 * i18n
	 *
	 * Obtains a message from the internationalization files with parameters.
	 *
	 * @deprecated Use the {@code Messages} interface with the {@code FacesMessages} implementation.
	 * @param property
	 * @param parameters
	 * @return
	 */
	@Deprecated
	public static String getMessage(String property, Object... parameters) {
		String message = Faces.getMessage(property);
		MessageFormat formatter = new MessageFormat(message);
		return formatter.format(parameters);
	}

}
