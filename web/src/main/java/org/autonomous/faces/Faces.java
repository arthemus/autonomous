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
 * Classe para centralizar utilitários comuns para uso do JSF.
 * 
 * @author arthemus
 * @since 26/09/2012
 */
public final class Faces {

	/**
	 * Diretorio raiz da aplicacao.
	 */
	public static final String FACES_CONTEXT_PATH = Faces.getRealPath();

	private static final String FILE_DOWNLOAD = "attachment";
	
	private static final String FILE_OPEN = "inline";
	
	/**
	 * Adiciona uma mensagem de erro no contexto da aplicacao para ser exibida
	 * em um determinado componente da tela.
	 * 
	 * @author arthemus
	 * @param message
	 *            Mensagem a ser exibida.
	 * @param target
	 *            Formulario de destino.
	 */
	public static final void addMessageError(String message, String target) {
		throwMessageContext(message, target, "Erro", FacesMessage.SEVERITY_ERROR);		
	}
	
	/**
	 * Adiciona uma mensagem de erro no contexto da aplicação contendo uma
	 * determinada mensagem, sumario e para um determinado componente.
	 * 
	 * @author arthemus
	 * @param message
	 *            Mensagem a ser exibida.
	 * @param target
	 *            Formulário de destino.
	 * @param detail
	 *            Resumo da mensagem, referência.
	 */
	public static final void addMessageError(String message, String target, String detail) {
		throwMessageContext(message, target, detail, FacesMessage.SEVERITY_ERROR);		
	}

	/**
	 * Adiciona uma mensagem de informacao no contexto da aplicacao para ser
	 * exibida em um determinado componente da tela.
	 * 
	 * @author arthemus
	 * @param message
	 *            Mensagem a ser exibida.
	 * @param target
	 *            Componente de destino.
	 */
	public static final void addMessageInfo(String message, String target) {
		throwMessageContext(message, target, "Informação", FacesMessage.SEVERITY_INFO);		
	}

	/**
	 * Adiciona uma mensagem de informação no contexto da aplicação contendo uma
	 * determinada mensagem, sumario e para um determinado componente.
	 * 
	 * @author arthemus
	 * @param message
	 *            Mensagem a ser exibida.
	 * @param target
	 *            Formulário de destino.
	 * @param detail
	 *            Resumo da mensagem, referência.
	 */
	public static final void addMessageInfo(String message, String target, String detail) {
		throwMessageContext(message, target, detail, FacesMessage.SEVERITY_INFO);		
	}

	/**
	 * Realiza o download de um determinado arquivo com base no Request e 
	 * Response correntes do FacesContext.
	 * 
	 * Exclusivo para páginas JSF.
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
	 * Realiza a abertura de um determinado arquivo diretamente no browser.
	 * Deve-se verificar se o browser em questão tem suporte nativo para o 
	 * tipo do arquivo, caso contrario, o mesmo será enviado ao browser por
	 * Download.
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
	 * Realiza o download de um determinado arquivo.
	 * 
	 * Funciona tanto com páginas JSF quanto para páginas JSP utilizando Servlet. 
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
	 * Realiza uma determinada operação com um arquivo.
	 * 
	 * @param request
	 * @param response
	 * @param file
	 * @param contentDisposition
	 *            Operação, pode ser um download ou uma abertura direta.
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
			throw new IOException("Erro ao processar arquivo: " + e.getMessage());
		}
	}

	/**
	 * Para obter o context atual.
	 * 
	 * @return
	 */
	private static ExternalContext externalContext() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		return external;
	}

	/**
	 * Obtem o contexto atual da aplicação.
	 * 
	 * @author arthemus
	 * @return Contexto atual.
	 */
	public static ExternalContext getExternaContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	/**
	 * Obtem o login do usuário logado.
	 * 
	 * @author arthemus
	 * @return Login.
	 */
	public static String getLoginSession() {
		ExternalContext external = externalContext();
		return external.getRemoteUser().trim();
	}

	/**
	 * Obtem um determinado objeto da Sessão atual.
	 * 
	 * @author arthemus
	 * @param objectName
	 *            Nome do objeto desejado.
	 * @return Objeto da sessão.
	 */
	public static Object getObjectSession(String objectName) {
		ExternalContext external = externalContext();
		HttpSession session = (HttpSession) external.getSession(true);
		return session.getAttribute(objectName);
	}

	/**
	 * Obtem o contexto da aplicacao.
	 * 
	 * @author arthemus
	 * @return Contexto raiz.
	 */
	private static String getRealPath() {
		FacesContext aFacesContext = FacesContext.getCurrentInstance();
		ServletContext context = (ServletContext) aFacesContext.getExternalContext().getContext();
		String realPath = context.getRealPath("/");
		return realPath;
	}
	
	/**
	 * Obtem a sessão Http atual.
	 * 
	 * @author arthemus
	 * @return Sessão atual.
	 */
	public static HttpSession getSession() {
		ExternalContext external = externalContext();
		HttpSession session = (HttpSession) external.getSession(true);
		return session;
	}
	
	/**
	 * Direciona a aplicacao para um determinado ponto. Util para obter a
	 * localizacao exata de arquivos externos em determinados diretorios do
	 * sistema.
	 * 
	 * @author arthemus
	 * @param path
	 *            Diretorio ou arquivo de destino.
	 * @return Caminho completo desde a raiz da aplicacao ate o destino
	 *         expecificado.
	 */
	public static String goTo(String path) {
		StringBuilder str = new StringBuilder(FACES_CONTEXT_PATH.length() * 2);
		str.append(FACES_CONTEXT_PATH);
		str.append("WEB-INF");
		str.append("/");
		str.append(path);
		return str.toString();
	}
	
	/**
	 * Guarda um determinado objeto na sessão atual.
	 * 
	 * @author arthemus
	 * @param name Nome do objeto para futura busca.
	 * @param value Objeto em questão.
	 */
	public static final void saveInSession(final String name, final Object value) {
		ExternalContext external = externalContext();
		HttpSession session = (HttpSession) external.getSession(true);
		session.setAttribute(name, value);
	}
	
	/**
	 * Sobe uma message no contexto atual da aplicação.
	 * 
	 * @param message Mensagem a ser enviada.
	 * @param target ID do componente à receber a mensagem.
	 * @param detail Detalhe da mensagem.
	 * @param severity Tipo, Informação, Erro, Atenção...
	 */
	private static final void throwMessageContext(String message, String target, String detail, Severity severity) {
		FacesMessage faceMsg = new FacesMessage();
		faceMsg.setSeverity(severity);
		faceMsg.setSummary(detail);
		faceMsg.setDetail(message);
		FacesContext.getCurrentInstance().addMessage(target, faceMsg);
	}
	
	/**
	 * Obtem o nome do arquivo informado para upload.
	 * 
	 * @param file Geralmente, um arquivo informado em campo upload.
	 * @return Nome do arquivo.
	 */
	public static String getName(final Part file) {
		String[] content = file.getHeader("content-disposition").split(";");
		for (String part : content) {
			if (part.trim().startsWith("filename")) {
				String filename = part.substring(part.indexOf("=") + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
			}
		}
		return new String("[Não foi possível obter o nome do Arquivo]");
	}
	
	/**
	 * Para obter o conteudo do arquivo.
	 * Funciona melhor para arquivos no formato texto como xml, jsons ou txt.
	 * Em caso de arquivos de imagem, docs ou pdf, o retorno pode conter diversos
	 * caracteres especiais (linguagem de maquina).
	 * 
	 * @param file Geralmente, um arquivo informado em campo RequestUpload.
	 * @return Conteudo do arquivo no formato texto.
	 * @throws IOException Caso o arquivo não possa ser lido.
	 */
	public static String getContent(final Part file) throws IOException {
		try {
			return new Scanner(file.getInputStream()).useDelimiter("\\A").next();
		} catch (IOException e) {
			throw new IOException("Não foi possível ler o Arquivo.\nErro: " + e.getMessage());
		}		
	}

	/**
	 * Obtem o número IP do servidor onde a aplicação está sendo executada.
	 * 
	 * @return
	 */
	public static String getServerIP() {
		FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        return request.getRemoteAddr().trim();
	}

	/**
	 * i18n
	 * 
	 * Obtem uma mensagem dos arquivos de internacionalização.
	 * 
	 * @deprecated Utilize a interface {@code Message18n} com a implementação {@code FacesMessage18n}.
	 * @param propriedade
	 * @return
	 */
	@Deprecated
	public static String getMessage(String propriedade) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		return bundle.getString(propriedade);
	}
	
	/**
	 * i18n
	 * 
	 * Obtem uma mensagem dos arquivos de internacionalização com parametros.
	 * 
	 * @deprecated Utilize a interface {@code Message18n} com a implementação {@code FacesMessage18n}.
	 * @param propriedade
	 * @param parametros
	 * @return
	 */
	@Deprecated
	public static String getMessage(String propriedade, Object... parametros) {
		String message = Faces.getMessage(propriedade);
		MessageFormat formatter = new MessageFormat(message);
		return formatter.format(parametros);
	}
	
}
