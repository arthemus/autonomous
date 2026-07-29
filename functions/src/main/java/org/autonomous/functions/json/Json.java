package org.autonomous.functions.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Utility class for handling JSON files.
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
	 * Obtains a new instance of the Json class.
	 *
	 * @param jsonString
	 *            The JSON string to parse.
	 * @return A new Json instance.
	 * @throws JsonFormatException
	 */
	public static final Json getInstance(final String jsonString)
			throws JsonFormatException {
		try {
			String jsonValid = getJsonValid(jsonString.replaceAll("[\"']", ""));
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
	 * Obtains a new validated JSON string. In this case, adds quotes to
	 * each node of the original file.
	 *
	 * @param json
	 *            The raw JSON string.
	 * @return The validated JSON string.
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
	 * Obtains the value of an element from the JSON file.
	 *
	 * @param nodeName
	 *            The name of the node to retrieve.
	 * @return The value of the element as a string.
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
