package org.autonomous.functions.json;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author arthemus
 * @since 03/10/2013
 */
public class JsonsTest {

	@Test
	public void shouldCreateInstanceWithValidJsonWithoutQuotes() throws JsonFormatException {
		Json.getInstance("{j_username:CPM,j_password:********************************,j_empresa:001}");
	}

	@Test
	public void shouldCreateInstanceWithValidJsonWithQuotes() throws JsonFormatException {
		Json.getInstance("{\"w_hfreanzr\":\"PCZ\",\"w_cnffjbeq\":\"9o10oq8o01r704s0s35pr4996r2o5ss6\",\"w_rzcerfn\":\"001\"}");
	}

	@Test
	public void shouldHandleJsonWithApostrophes() throws JsonFormatException {
		Json.getInstance("{'j_username':'CPM','j_password':'********************************','j_empresa':'001'}");
	}

	@Test
	public void shouldRefactorJsonWithoutQuotes() throws JsonFormatException {
		String invalidJson = "{j_username:CPM,j_password:********************************,j_empresa:001}";
		String validJson = "{\"j_username\":\"CPM\",\"j_password\":\"********************************\",\"j_empresa\":\"001\"}";
		String refactoredJson = Json.getInstance(invalidJson).toString();
		Assert.assertEquals(validJson, refactoredJson);
	}

}
