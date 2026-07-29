package org.autonomous.functions.zipcode;

/**
 * Exception thrown when an unexpected error occurs while accessing the
 * Correios zip code (CEP) lookup service.
 *
 * http://www.buscacep.correios.com.br/servicos/dnec/consultaLogradouroAction.do
 *
 * @author Fabio Franco Uechi
 */
public class ZipCodeServiceFailureException extends RuntimeException {

	private static final long serialVersionUID = 1462228622695384135L;

	public ZipCodeServiceFailureException(Throwable cause) {
		super(cause);
	}

}
