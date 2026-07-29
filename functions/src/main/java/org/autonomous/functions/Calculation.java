package org.autonomous.functions;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Class to encapsulate common mathematical rules within commercial systems
 * such as rounding, comparison, and simple mathematical operations like
 * addition, subtraction, multiplication, and division.
 *
 * @author arthemus
 * @since 18/12/2013
 */
public strictfp final class Calculation {

	private final BigDecimal value;

	private Calculation(final BigDecimal value) {
		this.value = value;
	}

	/**
	 * Obtains a new instance from a double value with or without decimal
	 * places.
	 *
	 * @param value
	 *            The initial value.
	 * @return A new Calculation instance.
	 */
	public static Calculation newInstance(final double value) {
		BigDecimal decimal = BigDecimal.valueOf(value);
		return new Calculation(decimal);
	}

	/**
	 * Obtains a new instance from a simple integer.
	 *
	 * @param value
	 *            The initial value.
	 * @return A new Calculation instance.
	 */
	public static Calculation newInstance(final int value) {
		BigDecimal decimal = BigDecimal.valueOf(value);
		return new Calculation(decimal);
	}

	/**
	 * Obtains a new instance from a string value. This value will be rounded
	 * according to the national currency rules (two decimal places) and
	 * converted to the standard format of the Calculation class.
	 *
	 * @param value
	 *            The initial value as a string.
	 * @return A new Calculation instance.
	 */
	public static Calculation newInstance(final String value) {
		double newValue = Money.getInstance(value).getValue();
		BigDecimal decimal = BigDecimal.valueOf(newValue);
		return new Calculation(decimal);
	}

	/**
	 * Obtains a new instance from a value encapsulated in a BigDecimal.
	 *
	 * @param bigDecimal
	 *            The initial value.
	 * @return A new Calculation instance.
	 */
	public static Calculation newInstance(BigDecimal bigDecimal) {
		return new Calculation(bigDecimal);
	}

	/**
	 * Returns the original value.
	 *
	 * @return The BigDecimal value.
	 */
	public BigDecimal getValue() {
		return this.value;
	}

	/**
	 * Obtains a new Calculation with the value rounded to the national
	 * currency standard (two decimal places).
	 *
	 * This type of rounding uses basic rules, examining the last digit.
	 *
	 * See more details at:
	 * {@link http://www.brasilescola.com/matematica/arredondando-numeros.htm}
	 *
	 * @return A new Calculation instance.
	 */
	public Calculation doRealRounding() {
		Money money = Money.getInstance(this.value.doubleValue());
		return Calculation.newInstance(money.toString());
	}

	/**
	 * Obtains a new calculation rounded to four decimal places.
	 *
	 * @return A new Calculation instance.
	 */
	public Calculation doRoundingByQuantity() {
		BigDecimal decimal = this.value.setScale(4, RoundingMode.CEILING);
		return Calculation.newInstance(decimal);
	}

	/**
	 * Obtains a new calculation rounded to ten decimal places.
	 *
	 * @return A new Calculation instance.
	 */
	public Calculation doRoundingByUnit() {
		BigDecimal decimal = this.value.setScale(10, RoundingMode.CEILING);
		return Calculation.newInstance(decimal);
	}

	/**
	 * Obtains a new calculation rounded to two decimal places.
	 *
	 * This type of rounding forces the last digit to round up if the value
	 * has more than two decimal places.
	 *
	 * Different behavior from the doRealRounding method which respects
	 * the mathematical rules for rounding.
	 *
	 * @return A new Calculation instance.
	 */
	public Calculation doRoundingByTotal() {
		BigDecimal decimal = this.value.setScale(2, RoundingMode.CEILING);
		return Calculation.newInstance(decimal);
	}

	/**
	 * Performs rounding according to the specified number of decimal places.
	 *
	 * Respects the mathematical rounding rules.
	 *
	 * @param decimalPlaces
	 *            The number of decimal places.
	 * @return A new Calculation instance.
	 */
	public Calculation doRounding(int decimalPlaces) {
		if (decimalPlaces < 0)
			throw new ArithmeticException();

		BigDecimal decimal = this.value.setScale(decimalPlaces, RoundingMode.HALF_EVEN);
		return Calculation.newInstance(decimal);
	}

	/**
	 * Returns a double value of the object.
	 *
	 * @return The double value.
	 */
	public double getDoubleValue() {
		return this.value.doubleValue();
	}

	/**
	 * Returns an integer value of the object, truncating its decimal places.
	 *
	 * @return The integer value.
	 */
	public int getIntValue() {
		return this.value.intValue();
	}

	/**
	 * Returns a new object with the current value + addend.
	 *
	 * @param addend
	 *            The value to add.
	 * @return A new Calculation instance.
	 */
	public strictfp Calculation doAdd(double addend) {
		return Calculation.newInstance(this.value.add(BigDecimal.valueOf(addend)));
	}

	/**
	 * Returns a new object with the current value - subtrahend.
	 *
	 * @param subtrahend
	 *            The value to subtract.
	 * @return A new Calculation instance.
	 */
	public strictfp Calculation doSubtract(double subtrahend) {
		return Calculation.newInstance(this.value.subtract(BigDecimal.valueOf(subtrahend)));
	}

	/**
	 * Returns a new object with the current value x multiplicand.
	 *
	 * @param multiplicand
	 *            The value to multiply by.
	 * @return A new Calculation instance.
	 */
	public strictfp Calculation doMultiply(double multiplicand) {
		return Calculation.newInstance(this.value.multiply(BigDecimal.valueOf(multiplicand)));
	}

	/**
	 * Returns a new object with the current value / divisor.
	 *
	 * @param divisor
	 *            The value to divide by.
	 * @return A new Calculation instance.
	 */
	public strictfp Calculation doDivide(double divisor) {
		return Calculation.newInstance(this.value.divide(BigDecimal.valueOf(divisor), MathContext.DECIMAL128));
	}

	@Override
	public boolean equals(Object obj) {
		return this.value.compareTo(((Calculation) obj).getValue()) == 0;
	}

	@Override
	public String toString() {
		return this.value.toString();
	}

}
