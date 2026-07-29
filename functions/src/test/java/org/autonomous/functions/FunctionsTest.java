package org.autonomous.functions;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class FunctionsTest {

	@Test
	public void shouldRemovePrepositionsFromPhrase() {
		String phrase = "A data de saída não está saindo no danfe, pois ao informar no ped venda data de entrega não sai no danfe";
		String cleaned = Functions.removePrepositions(phrase);
		Assert.assertEquals("data saída não está saindo danfe, pois informar ped venda data entrega não sai danfe", cleaned);
	}

	@Test
	public void shouldRemovePrepositionsFromAnotherPhrase() {
		String phrase = "Rotina para baixa de titulos no contas a receber";
		String cleaned = Functions.removePrepositions(phrase);
		Assert.assertEquals("Rotina baixa titulos contas receber", cleaned);
	}

	@Test
	public void shouldBuildPeriodDescription() {
		String result = Functions.getPeriodDescription(new Date(), new Date());
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String start = format.format(new Date());
		String end = format.format(new Date());
		Assert.assertEquals("From ".concat(start).concat(" to ").concat(end), result);
	}

	@Test
	public void shouldTruncateStringFromRightToLeft() {
		String value = "0,4 MULTIUSO BRANCO BRILHANTE(Quant.Ltda)";
		String newValue = Functions.getTruncateRightToLeft(value, 30);
		Assert.assertEquals("0,4 MULTIUSO BRANCO BRILHANTE(", newValue);
	}

	@Test
	public void shouldTruncateStringFromLeftToRight() {
		String value = "0,4 MULTIUSO BRANCO BRILHANTE(Quant.Ltda)";
		String newValue = Functions.getTruncateLeftToRight(value, 30);
		Assert.assertEquals("O BRANCO BRILHANTE(Quant.Ltda)", newValue);
	}

	@Test
	public void shouldTruncateNullString() {
		String value = null;
		String newValue = Functions.getTruncateRightToLeft(value, 1);
		Assert.assertTrue("".equals(newValue));
	}

	@Test
	public void shouldTruncateEmptyString() {
		String value = "";
		String newValue = Functions.getTruncateRightToLeft(value, 1);
		Assert.assertTrue("".equals(newValue));
	}

	@Test
	public void shouldTruncateStringLongerThanLength() {
		String value = "0,4 MULTIUSO BRANCO BRILHANTE(Quant.Ltda)"; // size = 41
		String newValue = Functions.getTruncateRightToLeft(value, 51);
		Assert.assertTrue((value.length() >= newValue.length()));
	}

	@Test
	public void shouldPrintVerticalList() {
		List<String> list = Arrays.asList("linha01", "linha02", "linha03");
		String result = Functions.printListVertical(list);
		Assert.assertNotNull(result);
		Assert.assertTrue(result.contains("\n"));
	}

	@Test
	public void shouldPadLeftWithZeros() {
		String document = "0256";
		String newDocument = Functions.padLeftZeros(document, 10);
		Assert.assertEquals("0000000256", newDocument);
	}

	@Test
	public void shouldGetParametersMap() {
		Object[][] array = {
				{ "chave1", new Integer(1) },
				{ "chave2", new Double(2) },
				{ "chave3", new String() },
				{ "chave4", new Character('1') },
				{ "chave5", new Object() }
			};
		Map<String, Object> params = Functions.getParameters(array);
		Assert.assertNotNull(params);
		Assert.assertTrue(params.size() == 5);
	}

	@Test
	public void shouldRoundNumberToTwoDecimalPlaces() {
		Double number = Functions.roundTo(156.5695545, 2);
		Double numberExpected = 156.57;

		Assert.assertEquals(numberExpected, number);
	}

	@Test
	public void shouldRoundNumberToThreeDecimalPlaces() {
		Double number = Functions.roundTo(156.567, 3);
		Double numberExpected = 156.567;

		Assert.assertEquals(numberExpected, number);
	}

	@Test
	public void shouldRoundNumberToFourDecimalPlaces() {
		Double number = Functions.roundTo(156.5671988, 4);
		Double numberExpected = 156.5672;

		Assert.assertEquals(numberExpected, number);
	}

	@Test(expected = ArithmeticException.class)
	public void shouldThrowOnNegativeDecimalPlaces() {
		Functions.roundTo(156.5671988, -4);
	}

	@Test
	public void shouldRoundNumberToZeroDecimalPlaces() {
		Double number = Functions.roundTo(156.616561, 0);
		Double numberExpected = 157d;

		Assert.assertEquals(numberExpected, number);
	}

}
