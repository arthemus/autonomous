package org.autonomous.functions.zipcode;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ZipCodeLookupTest {

	private static final String VALID_ZIP_CODE = "13084440";
	private static final ZipCode FLORDALISA_AMALIA_GRIGOL_COGHI = new ZipCode("13084440",
			"Rua Flordalisa Amália Grigol Coghi", "Jardim América", "Campinas",
			"SP");
	private static final ZipCode FLORDALISA_MEIRA_MONTE = new ZipCode("17065340",
			"Rua Flordalisa Meira Monte", "Núcleo Habitacional Ver", "Bauru",
			"SP");

	private ZipCodeService zipCodeService;

	@Before
	public void setUp() throws Exception {
		zipCodeService = ZipCodeServiceFactory.getZipCodeService();
	}

	@After
	public void tearDown() throws Exception {
		zipCodeService = null;
	}

	public void shouldFindByValidZipCode() {
		ZipCode zipCode = zipCodeService.findByZipCode(VALID_ZIP_CODE);
		assertThat(zipCode, equalTo(FLORDALISA_AMALIA_GRIGOL_COGHI));
	}

	public void shouldFindMultipleTimesByValidZipCode() {
		ZipCode zipCode = zipCodeService.findByZipCode(VALID_ZIP_CODE);
		assertThat(zipCode, equalTo(FLORDALISA_AMALIA_GRIGOL_COGHI));
		zipCode = zipCodeService.findByZipCode(VALID_ZIP_CODE);
		assertThat(zipCode, equalTo(FLORDALISA_AMALIA_GRIGOL_COGHI));
		zipCode = zipCodeService.findByZipCode(VALID_ZIP_CODE);
		assertThat(zipCode, equalTo(FLORDALISA_AMALIA_GRIGOL_COGHI));
	}

	@Test(expected=ZipCodeNotFoundException.class)
	public void shouldThrowExceptionForInvalidZipCode() {
		zipCodeService.findByZipCode("1308444000");
	}

	public void shouldFindByAddressWithOneMatch() {
		List<ZipCode> zipCodes = zipCodeService.findByAddress("Rua Flordalisa Amália Grigol Coghi");
		assertNotNull(zipCodes);
		assertThat(zipCodes.size(), equalTo(1));
		assertThat(zipCodes.get(0), equalTo(FLORDALISA_AMALIA_GRIGOL_COGHI));
	}

	public void shouldFindByAddressWithMultipleMatches() {
		List<ZipCode> zipCodes = zipCodeService.findByAddress("Flordalisa");
		assertNotNull(zipCodes);
		assertThat(zipCodes.size(), equalTo(2));
		assertThat(zipCodes.get(0), equalTo(FLORDALISA_MEIRA_MONTE));
		assertThat(zipCodes.get(1), equalTo(FLORDALISA_AMALIA_GRIGOL_COGHI));
	}

}
