package org.autonomous.functions.formatters;

/**
 * Marcara para um documento CNPJ.
 * 
 * @author arthemus
 * @since 28/05/2013
 */
public final class Cnpj implements Formatter {

	@Override
	public String getMascara() {
		return "##.###.###/####-##";
	}
}
