package org.autonomous.tenaz.core;

/**
 * Exceções durante a conexão com um banco de dados.
 * 
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public final class ConexaoException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConexaoException() {
		super("Problemas na Conex�o com o Banco de Dados");
	}

	public ConexaoException(String message) {
		super(message);
	}

	public ConexaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConexaoException(Throwable cause) {
		super(cause);
	}

}
