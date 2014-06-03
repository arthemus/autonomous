package org.autonomous.tenaz.core;

import java.sql.SQLException;

/**
 * Classe para identificar uma exceção durante a persistencia.
 * 
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public class PersistException extends SQLException {

	private static final long serialVersionUID = 1L;

	public PersistException() {
		super("Problemas durante a persistencia dos dados.");
	}

	public PersistException(String mensagem) {
		super(mensagem);
	}
}
