package org.autonomous.functions.mail;

import java.net.MalformedURLException;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Class responsible for handling and sending e-mails.
 *
 * @author Arthemus C. Moreira
 * @since 27/02/2012
 * @see MailService
 * @version 27/02/2012 - 09:32:23 - File creation. (ticket 16414).
 *          <p>
 *          09/03/2012 - 11:40:55 - Improvement to send multiple e-mails.
 */
public class Email {

	private final MailService mailService;

	private Email(MailService mailService) {
		this.mailService = mailService;
	}

	/**
	 * Creates a new Email instance.
	 *
	 * @param mailService
	 *            The mail service configuration.
	 * @return A new Email instance.
	 */
	public static Email create(MailService mailService) {
		return new Email(mailService);
	}

	/**
	 * Sends a simple e-mail without additional formatting, recommended for
	 * cases where you want to send only text messages to the recipient,
	 * without any formatting or HTML tags.
	 *
	 * @throws EmailException
	 */
	public void sendSimple() throws EmailException {
		SimpleEmail email = new SimpleEmail();
		email.setMsg(mailService.getMessage());
		doSend(email);
	}

	/**
	 * Recommended for sending more complex e-mails with HTML formatting.
	 *
	 * @throws MalformedURLException
	 * @throws EmailException
	 */
	public void sendHtml() throws MalformedURLException, EmailException {
		HtmlEmail email = new HtmlEmail();
		email.setTextMsg("Your mail server does not support HTML formatted messages");
		email.setHtmlMsg(mailService.getMessage());
		doSend(email);
	}

	/**
	 * Method for sending the e-mail.
	 *
	 * @param email
	 *            The e-mail instance to send.
	 * @throws EmailException
	 */
	private void doSend(org.apache.commons.mail.Email email) throws EmailException {
		EmailValidator validator = EmailValidator.getInstance();
		email.setFrom(mailService.getSenderEmail(), mailService.getSenderName());
		String emails[] = mailService.getRecipientEmail().split("\\;");
		for (String address : emails) {
			if (validator.isValid(address.trim())) {
				email.addTo(address.trim(), mailService.getRecipientName());
			} else {
				throw new EmailException("The e-mail address ".concat(
						address).concat(" is not in a valid format!"));
			}
		}
		email.setSubject(mailService.getSubject());
		email.setHostName(mailService.getHost());
		email.setAuthentication(mailService.getUsername(), mailService.getPassword());
		email.setSmtpPort(mailService.getSmtpPort());
		email.setSSLOnConnect(mailService.isSSL());
		email.setStartTLSEnabled(mailService.isTLS());
		email.send();
	}
}
