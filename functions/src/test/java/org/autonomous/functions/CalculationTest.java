package org.autonomous.functions;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the Calculation class.
 *
 * @author arthemus
 * @since 18/12/2013
 */
public class CalculationTest {

	@Test
	public void shouldCreateCalculationFromDouble() {
		double value = Math.PI;
		Calculation calculation = Calculation.newInstance(value);
		Assert.assertEquals(3.141592653589793, calculation.getValue().doubleValue(), 0);
	}

	@Test
	public void shouldCreateCalculationFromDoubleWithRounding() {
		double value = 2539.0050251256;
		Calculation calculation = Calculation.newInstance(value).doRealRounding();
		Assert.assertEquals(2539.01, calculation.getValue().doubleValue(), 0);
	}

	@Test
	public void shouldRoundByQuantity() {
		double value = Math.PI;
		Calculation calculation = Calculation.newInstance(value).doRoundingByQuantity();
		Assert.assertEquals(3.1416, calculation.getValue().doubleValue(), 0);
	}

	@Test
	public void shouldRoundByUnit() {
		double value = Math.PI;
		Calculation calculation = Calculation.newInstance(value).doRoundingByUnit();
		Assert.assertEquals(3.1415926536, calculation.getValue().doubleValue(), 0);
	}

	@Test
	public void shouldRoundByTotal() {
		double value = Math.PI;
		Calculation calculation = Calculation.newInstance(value).doRoundingByTotal();
		Assert.assertEquals(3.15, calculation.getValue().doubleValue(), 0);
	}

	@Test
	public void shouldCompareValues() {
		Calculation c1 = Calculation.newInstance(15.23456).doRoundingByQuantity();
		Calculation c2 = Calculation.newInstance(15.23457).doRoundingByQuantity();
		Assert.assertTrue(c1.equals(c2));
	}

	@Test
	public void shouldPrintDoubleValue() {
		Calculation calculation = Calculation.newInstance(Math.PI);
		Assert.assertEquals(3.141592653589793, calculation.getDoubleValue(), 0);
	}

	@Test
	public void shouldPrintIntegerValue() {
		Calculation calculation = Calculation.newInstance(Math.PI);
		Assert.assertEquals(3, calculation.getIntValue(), 0);
	}

	@Test
	public void shouldAddWithoutRounding() {
		Calculation calculation = Calculation.newInstance(32.65).doAdd(0.3512);
		Assert.assertEquals(33.0012, calculation.getDoubleValue(), 0);
	}

	@Test
	public void shouldSubtractWithoutRounding() {
		Calculation calculation = Calculation.newInstance(32.65).doSubtract(0.3512);
		Assert.assertEquals(32.2988, calculation.getDoubleValue(), 0);
	}

	@Test
	public void shouldMultiplyWithoutRounding() {
		Calculation calculation = Calculation.newInstance(32.65).doMultiply(0.3512);
		Assert.assertEquals(11.46668, calculation.getDoubleValue(), 0);
	}

	@Test
	public void shouldDivideWithoutRounding() {
		Calculation calculation = Calculation.newInstance(32.65).doDivide(2);
		Assert.assertEquals(16.325, calculation.getDoubleValue(), 0);
	}

	@Test
	public void shouldDivideWithFractionalDivisor() {
		Calculation calculation = Calculation.newInstance(14.2718).doDivide(92.316);
		Assert.assertEquals(0.15459725291390441, calculation.getDoubleValue(), 0);
	}

	@Test
	public void shouldRoundToTwoDecimalPlaces() {
		Calculation calc = Calculation.newInstance(150.265944).doRounding(2);
		Assert.assertEquals(150.27, calc.getDoubleValue(), 0);
	}

	@Test
	public void shouldRoundToThreeDecimalPlaces() {
		Calculation calc = Calculation.newInstance(150.265944).doRounding(3);
		Assert.assertEquals(150.266, calc.getDoubleValue(), 0);
	}

	@Test
	public void shouldRoundToThreePlacesAfterMultiplying() {
		Calculation calc = Calculation.newInstance(150.236).doMultiply(1.563).doRounding(3);
		Assert.assertEquals(234.819, calc.getDoubleValue(), 0);
	}

	@Test(expected = ArithmeticException.class)
	public void shouldThrowOnNegativePlacesAfterMultiplying() {
		Calculation calc = Calculation.newInstance(150.236).doMultiply(1.563).doRounding(-3);
		Assert.assertEquals(234.819, calc.getDoubleValue(), 0);
	}

	@Test
	public void shouldRoundToZeroPlacesAfterMultiplying() {
		Calculation calc = Calculation.newInstance(150.236).doMultiply(1.563).doRounding(0);
		Assert.assertEquals(235, calc.getDoubleValue(), 0);
	}

}
