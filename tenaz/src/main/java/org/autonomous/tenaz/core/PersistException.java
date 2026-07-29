package org.autonomous.tenaz.core;

import java.sql.SQLException;

/**
 * Class to identify an exception during persistence.
 *
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public class PersistException extends SQLException {

	private static final long serialVersionUID = 1L;

	public PersistException() {
		super("Problems during data persistence.");
	}

	public PersistException(String message) {
		super(message);
	}
}
