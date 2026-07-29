package org.autonomous.functions;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the Money class.
 *
 * @author arthemus
 * @since 18/12/2013
 */
public class MoneyTest {

	@Test
	public void shouldPrintValueWithoutDecimalPlaces() {
		double value = 10;
		Money money = Money.getInstance(value);
		Assert.assertEquals("R$ 10,00", money.getValueForDisplay());
	}

	@Test
	public void shouldPrintComplexValueWithDecimalPlaces() {
		double value = 10345.98;
		Money money = Money.getInstance(value);
		Assert.assertEquals("R$ 10.345,98", money.getValueForDisplay());
	}

	/**
	 * See rounding rules:
	 *
	 * http://www.brasilescola.com/matematica/arredondando-numeros.htm
	 */
	@Test
	public void shouldPrintWithoutPerformingRounding() {
		double value = 10345.985;
		Money money = Money.getInstance(value);
		Assert.assertEquals("R$ 10.345,99", money.getValueForDisplay());
	}

}
