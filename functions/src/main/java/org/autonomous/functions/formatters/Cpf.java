package org.autonomous.functions.formatters;

/**
 * Marcara para um documento CPF.
 * 
 * @author arthemus
 * @since 28/05/2013
 */
public final class Cpf implements Formatter {
	
	@Override
	public String getMascara() {
		return "###.###.###-##";
	}

}
