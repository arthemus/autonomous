package org.autonomous.functions.messaging;

/**
 * Interface representing classes that work with internationalization and
 * messaging.
 *
 * @author arthemus
 * @since 16/05/2014
 *
 */
public interface Messages {

	/**
	 * Obtains a message configured in the internationalization files.
	 *
	 * @param property
	 *            The property key of the message.
	 * @return The resolved message.
	 */
	String getMessage(String property);

	/**
	 * Obtains a message configured in the internationalization files based
	 * on some parameters.
	 *
	 * @param property
	 *            The property key of the message.
	 * @param parameters
	 *            The parameters to interpolate into the message.
	 * @return The resolved and formatted message.
	 */
	String getMessage(String property, Object... parameters);
}
