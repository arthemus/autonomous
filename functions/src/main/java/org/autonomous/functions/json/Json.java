package org.autonomous.functions.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Classe utilitária para tratar arquivos JSON.
 * 
 * @author arthemus
 * @since 03/10/2013
 */
public final class Json {

	private final JsonObject jsonObject;
	private final String jsonString;

	private Json(final JsonObject jsonObject, final String jsonString) {
		this.jsonObject = jsonObject;
		this.jsonString = jsonString;
	}

	/**
	 * Obtem uma nova instancia da classe Json
	 * 
	 * @param stringJson
	 * @return
	 * @throws Exception
	 */
	public static final Json getInstance(final String stringJson)
			throws JsonFormatException {
		try {		
			String jsonValid = getJsonValid(stringJson.replaceAll("[\"']", ""));
			JsonElement element = new JsonParser().parse(jsonValid);
			if (element.isJsonObject())
				return new Json((JsonObject) element, jsonValid);
			else
				return new Json(new JsonObject(), jsonValid);
		} catch (Exception e) {
			throw new JsonFormatException(e.getMessage());
		}
	}
	
	/**
	 * Obtem uma nova String json já validada.
	 * No caso, adiciona aspas em cada nó do arquivo original.
	 * 
	 * @param json
	 * @return
	 */
	public static final String getJsonValid(final String json) {
		String jsonTemp = json.replaceAll("[{}]", "");
		StringBuilder builder = new StringBuilder(1024);
		String[] nodes = jsonTemp.split("\\,|:");
		for (int count = 0; count < nodes.length; count++) {
			String node = nodes[count];
			builder.append("\"".concat(node).concat("\""));
			if (count % 2 == 0) {
				builder.append(":");
			} else if (count + 1 < nodes.length) {
				builder.append(",");
			}
		} 
		return "{".concat(builder.toString().trim()).concat("}").trim();
	}

	/**
	 * Obtem o valor de um elemento do arquivo json.
	 * 
	 * @param nodeName
	 * @return
	 */
	public final String getElement(final String nodeName) {
		JsonElement element = jsonObject.get(nodeName);
		String resultValue = new String();
		if (element != null)
			resultValue = element.getAsString();
		return resultValue;
	}

	@Override
	public String toString() {
		return new String(jsonString);
	}	
	
}
