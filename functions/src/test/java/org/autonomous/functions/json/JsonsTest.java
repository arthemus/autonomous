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
	public void instanciaNovoServicoComJsonValidoSemAspas() throws JsonFormatException {
		Json.getInstance("{j_username:CPM,j_password:9b10bd8b01e704f0f35ce4996e2b5ff6,j_empresa:001}");		
	}
	
	@Test
	public void instanciaNovoServicoComJsonComAspas() throws JsonFormatException {
		Json.getInstance("{\"w_hfreanzr\":\"PCZ\",\"w_cnffjbeq\":\"9o10oq8o01r704s0s35pr4996r2o5ss6\",\"w_rzcerfn\":\"001\"}");
	}
	
	@Test
	public void testaJsonComApostrofos() throws JsonFormatException {
		Json.getInstance("{'j_username':'CPM','j_password':'9b10bd8b01e704f0f35ce4996e2b5ff6','j_empresa':'001'}");
	}
	
	@Test
	public void refatoraJsonSemAspas() throws JsonFormatException {
		String jsonInvalido = "{j_username:CPM,j_password:9b10bd8b01e704f0f35ce4996e2b5ff6,j_empresa:001}";
		String jsonValido = "{\"j_username\":\"CPM\",\"j_password\":\"9b10bd8b01e704f0f35ce4996e2b5ff6\",\"j_empresa\":\"001\"}";
		String jsonRefatorado = Json.getInstance(jsonInvalido).toString();
		Assert.assertEquals(jsonValido, jsonRefatorado);
	}
	
}
