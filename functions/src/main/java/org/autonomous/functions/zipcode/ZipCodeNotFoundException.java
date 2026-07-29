package org.autonomous.functions.zipcode;

/**
 * Exception thrown when a zip code (CEP) search returns no results.
 *
 * @author Fabio Franco Uechi
 */
public class ZipCodeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ZipCodeNotFoundException(int zipCode, Throwable cause) {
		super("Zip code " + zipCode + " was not found.", cause);
	}

	public ZipCodeNotFoundException(String query, Throwable cause) {
		super("Search for '" + query + "' returned no results.", cause);
	}

}
