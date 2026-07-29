package org.autonomous.functions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Currency;
import java.util.Locale;

/**
 * Converts and prints values in the national currency format (Real - BRL).
 *
 * @author arthemus
 * @since 18/12/2013
 */
public final class Money {

	private static final Locale LOCALE = new Locale("pt", "BR");

	private static final Currency CURRENCY = Currency.getInstance("BRL");

	private static final DecimalFormat DECIMAL_FORMAT = (DecimalFormat) NumberFormat.getNumberInstance(LOCALE);

	static {
		DECIMAL_FORMAT.applyPattern("#,##0.00");
	}

	private final double value;

	private Money(final double value) {
		this.value = value;
	}

	/**
	 * Obtains a new instance of the class from a simple double value.
	 *
	 * @param value
	 *            The initial value.
	 * @return A new Money instance.
	 */
	public static Money getInstance(final double value) {
		return new Money(value);
	}

	/**
	 * Obtains a new instance of the class from a string value. This value
	 * will be converted and rounded so it can be printed in the national
	 * currency standard.
	 *
	 * @param value
	 *            The initial value as a string.
	 * @return A new Money instance.
	 */
	public static Money getInstance(final String value) {
		double result = 0D;
		try {
			result = DECIMAL_FORMAT.parse(value).doubleValue();
		} catch (ParseException e) {
			throw new ArithmeticException("Problems converting the value ".concat(value));
		}
		return new Money(result);
	}

	/**
	 * Obtains the original value. If the instance was created from a string
	 * value, the return will be rounded.
	 *
	 * @return The double value.
	 */
	public double getValue() {
		return this.value;
	}

	/**
	 * Obtains an ideal return for display to the user, including the
	 * currency symbol with the value.
	 *
	 * Ex: R$ 15,00
	 *
	 * @return The formatted value for display.
	 */
	public String getValueForDisplay() {
		return CURRENCY.getSymbol(LOCALE).concat(" ").concat(toString());
	}

	@Override
	public String toString() {
		return DECIMAL_FORMAT.format(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Money other = (Money) obj;
		if (Double.doubleToLongBits(value) != Double
				.doubleToLongBits(other.value))
			return false;
		return true;
	}

}
