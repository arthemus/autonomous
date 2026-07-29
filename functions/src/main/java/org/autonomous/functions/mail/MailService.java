package org.autonomous.functions.mail;

/**
 * Interface representing the e-mail to be handled by the system.
 *
 * @author arthemus
 * @since 27/02/2012
 * @version 27/02/2012 - 09:13:39 - File creation. (ticket 16141).
 */
public interface MailService {

	/**
	 *
	 * @return The address of the mail server to be used.
	 */
	String getHost();

	/**
	 *
	 * @return The username for server access.
	 */
	String getUsername();

	/**
	 *
	 * @return The password for server access.
	 */
	String getPassword();

	/**
	 *
	 * @return The name of the e-mail recipient.
	 */
	String getRecipientName();

	/**
	 *
	 * @return The e-mail address of the recipient.
	 */
	String getRecipientEmail();

	/**
	 *
	 * @return The name of the sender, usually the same as the server access
	 *         username.
	 */
	String getSenderName();

	/**
	 *
	 * @return The e-mail address of the sender, usually the same e-mail
	 *         informed in the getUsername() method.
	 */
	String getSenderEmail();

	/**
	 *
	 * @return The e-mail subject.
	 */
	String getSubject();

	/**
	 *
	 * @return The message. Can be plain text or even text formatted with
	 *         HTML tags.
	 */
	String getMessage();

	/**
	 *
	 * @return The SMTP port number of the server.
	 */
	int getSmtpPort();

	/**
	 *
	 * @return true or false to determine whether SSL encryption should be
	 *         used.
	 */
	boolean isSSL();

	/**
	 *
	 * @return true or false to determine whether TLS encryption should be
	 *         used.
	 */
	boolean isTLS();

}
