package org.autonomous.functions.zipcode;

import java.util.List;

/**
 * Exposes the basic zip code (CEP) lookup services.
 *
 * @author Fabio Franco Uechi
 */
public interface ZipCodeService {

	/**
	 * Obtains a <code>{@link ZipCode}</code> by its zip code number.
	 *
	 * @param zipCode
	 *            The zip code number to search for.
	 * @return The <code>{@link ZipCode}</code> found.
	 * @throws ZipCodeNotFoundException
	 *             if the provided number returns no results.
	 * @throws ZipCodeServiceFailureException
	 *             in case of a service failure.
	 */
	public ZipCode findByZipCode(String zipCode);

	/**
	 * Obtains the <code>{@link ZipCode}</code>s that match the provided term.
	 *
	 * @param query
	 *            Term used for the zip code search. Usually the name (or part)
	 *            of a logradouro (public thoroughfare).
	 * @return List of <code>{@link ZipCode}</code>s found.
	 * @throws ZipCodeServiceFailureException
	 *             in case of a service failure.
	 */
	public List<ZipCode> findByAddress(String query);

}
