package org.autonomous.functions.zipcode;

/**
 * Factory for ZipCodeService.
 */
public class ZipCodeServiceFactory {

	/**
	 * @return a thread-safe instance of ZipCodeService.
	 */
	public static ZipCodeService getZipCodeService() {
		return new ZipCodeLookup();
	}

}
