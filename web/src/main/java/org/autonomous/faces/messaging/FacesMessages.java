package org.autonomous.faces.messaging;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.autonomous.functions.messaging.Messages;
import org.autonomous.functions.messaging.UnitMessages;

/**
 * Implementation of the internationalization system based on the JSF context.
 *
 * @author arthemus
 * @since 16/05/2014
 * @see Messages
 * @see UnitMessages
 *
 */
public class FacesMessages implements Messages {

	@Override
	public String getMessage(String property) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
		return bundle.getString(property);
	}

	@Override
	public String getMessage(String property, Object... parameters) {
		String message = this.getMessage(property);
		MessageFormat formatter = new MessageFormat(message);
		return formatter.format(parameters);
	}
}
