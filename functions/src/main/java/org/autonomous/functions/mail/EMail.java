package org.autonomous.functions.mail;

import java.net.MalformedURLException;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Classe responsavel por tratar e enviar E-Mails.
 * 
 * @author Arthemus C. Moreira
 * @since 27/02/2012
 * @see IMail
 * @version 27/02/2012 - 09:32:23 - Criação do arquivo. (ficha 16414).
 *          <p>
 *          09/03/2012 - 11:40:55 - Melhoria para enviar multiplos emails.
 */
public class EMail {

	private final IMail _imail;

	private EMail(IMail email) {
		_imail = email;
	}

	/**
	 * 
	 * @param iMail
	 * @return
	 */
	public static EMail novo(IMail iMail) {
		return new EMail(iMail);
	}
	
	/**
	 * Enviar um e-mail simples sem maiores formatações, indicado nos casos em
	 * que deseja-se enviar apenas mensagens de texto ao destinatário, sem
	 * qualquer formatação ou tag html.
	 * 
	 * @throws EmailException
	 */
	public void doEnvioSimples() throws EmailException {
		SimpleEmail email = new SimpleEmail();
		email.setMsg(_imail.getMensagem());
		doSend(email);
	}

	/**
	 * Indicado para envio de e-mails mais complexos e com formatação Html.
	 * 
	 * @throws MalformedURLException
	 * @throws EmailException
	 */
	public void doEnvioHtml() throws MalformedURLException, EmailException {
		HtmlEmail email = new HtmlEmail();
		email.setTextMsg("Seu servidor de E-Mail não suporta mensagens no formato HTML");
		email.setHtmlMsg(_imail.getMensagem());
		doSend(email);
	}

	/**
	 * Método para envio do E-Mail.
	 * 
	 * @param _imail
	 *            Instancia da classe de E-Mail.
	 * @throws EmailException
	 */
	private void doSend(Email email) throws EmailException {
		EmailValidator valida = EmailValidator.getInstance();
		email.setFrom(_imail.getRemetenteEmail(), _imail.getRemetenteNome());
		String emails[] = _imail.getDestinatarioEmail().split("\\;");
		for (String endereco : emails) {
			if (valida.isValid(endereco.trim())) {
				email.addTo(endereco.trim(), _imail.getDestinatarioNome());
			} else {
				throw new EmailException("O endereço de e-mail ".concat(
						endereco).concat(" não está em um formato válito!"));
			}
		}
		email.setSubject(_imail.getAssunto());
		email.setHostName(_imail.getHost());
		email.setAuthentication(_imail.getUsuario(), _imail.getSenha());
		email.setSmtpPort(_imail.getSmtp());
		email.setSSLOnConnect(_imail.isSSL());
		email.setStartTLSEnabled(_imail.isTLS());
		email.send();
	}
}