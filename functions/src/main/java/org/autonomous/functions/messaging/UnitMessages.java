package org.autonomous.functions.messaging;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Implementation of the internationalization system for use in unit tests.
 *
 * @author arthemus
 * @since 16/05/2014
 * @see Messages
 * @see FacesMessages
 *
 */
public class UnitMessages implements Messages {

	@Override
	public String getMessage(String property) {
		ResourceBundle bundle = ResourceBundle.getBundle("messages", new Locale("pt", "BR"));
		String message = bundle.getString(property);
		return message;
	}

	@Override
	public String getMessage(String property, Object... parameters) {
		String message = this.getMessage(property);
		MessageFormat formatter = new MessageFormat(message);
		return formatter.format(parameters);
	}

}
