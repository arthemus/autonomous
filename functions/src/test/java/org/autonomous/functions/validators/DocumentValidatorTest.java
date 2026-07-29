package org.autonomous.functions.validators;

import org.junit.Assert;
import org.junit.Test;

public class DocumentValidatorTest {

	/**
	 * CPF tests
	 */

	@Test
	public void shouldRejectInvalidCpf() {
		boolean result = DocumentValidator.isCpf("04402024691");
		Assert.assertEquals(false, result);
	}

	@Test
	public void shouldAcceptValidCpf() {
		boolean result = DocumentValidator.isCpf("04402024692");
		Assert.assertEquals(true, result);
	}

	@Test
	public void shouldRejectCpfWithAllSameDigits() {
		boolean result = DocumentValidator.isCpf("44444444444");
		Assert.assertEquals(false, result);
	}

	@Test
	public void shouldRejectCpfWithAllZeros() {
		boolean result = DocumentValidator.isCpf("00000000000");
		Assert.assertEquals(false, result);
	}

	@Test
	public void shouldRejectCpfWithLetters() {
		boolean result = DocumentValidator.isCpf("A4402024692");
		Assert.assertEquals(false, result);
	}

	@Test
	public void shouldAcceptValidCpfWithDotsAndDash() {
		boolean result = DocumentValidator.isCpf("044.020.246-92");
		Assert.assertEquals(true, result);
	}

	@Test
	public void shouldRejectInvalidCpfWithWrongSeparators() {
		boolean result = DocumentValidator.isCpf("044.020246-92");
		Assert.assertEquals(false, result);
	}

	/**
	 * CNPJ tests
	 */

	@Test
	public void shouldAcceptValidCnpj() {
		boolean result = DocumentValidator.isCnpj("16832651000340");
		Assert.assertEquals(true, result);
	}

	@Test
	public void shouldRejectInvalidCnpj() {
		boolean result = DocumentValidator.isCnpj("16832351000340");
		Assert.assertEquals(false, result);
	}

	@Test
	public void shouldRejectCnpjWithAllZeros() {
		boolean result = DocumentValidator.isCnpj("00000000000000");
		Assert.assertEquals(false, result);
	}

	@Test
	public void shouldRejectCnpjWithAllSameDigits() {
		boolean result = DocumentValidator.isCnpj("55555555555555");
		Assert.assertEquals(false, result);
	}

	@Test
	public void shouldRejectCnpjWithLetters() {
		boolean result = DocumentValidator.isCnpj("1683265A000340");
		Assert.assertEquals(false, result);
	}

	@Test
	public void shouldAcceptValidCnpjWithSeparators() {
		boolean result = DocumentValidator.isCnpj("16.832.651/0003-40");
		Assert.assertEquals(true, result);
	}

	@Test
	public void shouldRejectInvalidCnpjWithWrongSeparators() {
		boolean result = DocumentValidator.isCnpj("16.832651-0003-40");
		Assert.assertEquals(false, result);
	}

	/* State Registration tests */

	@Test
	public void shouldAcceptSaoPauloStateRegistration() {
		boolean result = DocumentValidator.isStateRegistration(FederativeUnit.SP, "116696126113");
		Assert.assertEquals(true, result);
	}

	@Test
	public void shouldRejectSaoPauloStateRegistration() {
		boolean result = DocumentValidator.isStateRegistration(FederativeUnit.SP, "116696126110");
		Assert.assertEquals(false, result);
	}

	@Test
	public void shouldRejectMinasGeraisStateRegistration() {
		boolean result = DocumentValidator.isStateRegistration(FederativeUnit.MG, "116696126113");
		Assert.assertEquals(false, result);
	}

	@Test
	public void shouldAcceptMinasGeraisStateRegistration() {
		boolean result = DocumentValidator.isStateRegistration(FederativeUnit.MG, "5572192270035");
		Assert.assertEquals(true, result);
	}

	@Test
	public void shouldRejectStateRegistrationWithAllZeros() {
		boolean result = DocumentValidator.isStateRegistration(FederativeUnit.MG, "0000000000000");
		Assert.assertEquals(false, result);
	}
}
