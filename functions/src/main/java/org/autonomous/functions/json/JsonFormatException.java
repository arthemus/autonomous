package org.autonomous.functions.json;

/**
 * Standard exception for operations with JSON files.
 *
 * @author arthemus
 * @since 03/10/2013
 */
public final class JsonFormatException extends Exception {

	private static final long serialVersionUID = 1L;

	public JsonFormatException() {
		super();
	}

	public JsonFormatException(String message) {
		super(message);
	}

	public JsonFormatException(Throwable exception) {
		super(exception);
	}
}
