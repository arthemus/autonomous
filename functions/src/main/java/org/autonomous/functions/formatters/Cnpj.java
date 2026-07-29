package org.autonomous.functions.formatters;

/**
 * Mask for a CNPJ document.
 *
 * @author arthemus
 * @since 28/05/2013
 */
public final class Cnpj implements Formatter {

	@Override
	public String getMask() {
		return "##.###.###/####-##";
	}
}
