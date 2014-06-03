package org.autonomous.tenaz.commons;


/**
 * Exceções decorrentes da utilização de uma classe de cadastro.
 * 
 * @author arthemus
 * @since 06/11/2013
 */
public class CrudException extends Exception {

	private static final long serialVersionUID = 1L;

	public CrudException() {
		super();
	}

	public CrudException(String message) {
		super(message);		
	}

	public CrudException(Throwable cause) {
		super(cause);		
	}

	public CrudException(String message, Throwable cause) {
		super(message, cause);		
	}

}
