package org.autonomous.functions.mail;

/**
 * Interface para representação do E-Mail a ser trabalhado pelo sistema.
 * 
 * @author arthemus
 * @since 27/02/2012
 * @version 27/02/2012 - 09:13:39 - Criação do arquivo. (ficha 16141).
 */
public interface IMail {

	/**
	 * 
	 * @return Endereço do servidor de e-mails a ser utilizado.
	 */
	String getHost();

	/**
	 * 
	 * @return Usuário de acesso ao servidor.
	 */
	String getUsuario();

	/**
	 * 
	 * @return Senha de acesso ao servidor.
	 */
	String getSenha();

	/**
	 * 
	 * @return Nome do destinátario do e-mail.
	 */
	String getDestinatarioNome();

	/**
	 * 
	 * @return E-Mail do destinatario.
	 */
	String getDestinatarioEmail();

	/**
	 * 
	 * @return Nome do remetente, geralmente o mesmo nome do usuário de acesso
	 *         ao Servidor.
	 */
	String getRemetenteNome();

	/**
	 * 
	 * @return E-Mail do remetente, geralmente o mesmo e-mail informado no
	 *         método getUsuario().
	 */
	String getRemetenteEmail();

	/**
	 * 
	 * @return Assunto do e-mail.
	 */
	String getAssunto();

	/**
	 * 
	 * @return Mensagem. Pode ser um texto simples ou até mesmo um texto
	 *         formatato com tags Html.
	 */
	String getMensagem();

	/**
	 * 
	 * @return Número da porta Smtp do servidor.
	 */
	int getSmtp();

	/**
	 * 
	 * @return true ou false para verificar se deve ou não conter criptografia
	 *         SSL.
	 */
	boolean isSSL();

	/**
	 * 
	 * @return true ou false para verificar se deve ou não conter criptografia
	 *         TLS.
	 */
	boolean isTLS();

}