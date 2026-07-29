package org.autonomous.functions.formatters;

/**
 * Mask for a CPF document.
 *
 * @author arthemus
 * @since 28/05/2013
 */
public final class Cpf implements Formatter {

	@Override
	public String getMask() {
		return "###.###.###-##";
	}

}
