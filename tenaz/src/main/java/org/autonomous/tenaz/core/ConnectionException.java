package org.autonomous.tenaz.core;

/**
 * Exceptions raised while connecting to a database.
 *
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public final class ConnectionException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConnectionException() {
		super("Problems connecting to the database");
	}

	public ConnectionException(String message) {
		super(message);
	}

	public ConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectionException(Throwable cause) {
		super(cause);
	}

}
