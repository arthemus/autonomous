package org.autonomous.functions;

/**
 * Exceções durante a conversão de arquivos com a classe Stream.
 * 
 * @author arthemus
 * @since 20/12/2013
 */
public class StreamException extends Exception {

	private static final long serialVersionUID = 1L;

	public StreamException() {
		super();
	}

	public StreamException(String message) {
		super(message);
	}
}
