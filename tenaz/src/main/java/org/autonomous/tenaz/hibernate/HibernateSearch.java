package org.autonomous.tenaz.hibernate;

import java.util.List;
import java.util.Map;

import org.autonomous.tenaz.core.PersistException;

/**
 * Provides a generic way to query the database using Hibernate.
 *
 * Useful for common and repetitive operations across different entities.
 *
 * @author arthemus
 * @since 28/08/2013
 */
public interface HibernateSearch {

	/**
	 * Returns a single entity resulting from the database search.
	 *
	 * @param query
	 *            SQL or NamedQuery.
	 * @param params
	 *            Map of parameters, or {@code null} when there are none.
	 * @return The single result, or {@code null} when nothing is found.
	 * @throws PersistException
	 *             If the search fails.
	 */
	<T> T getUniqueResult(final String query, final Map<String, Object> params)
			throws PersistException;

	/**
	 * Returns a list of results.
	 *
	 * @param query
	 *            SQL or NamedQuery.
	 * @param params
	 *            Map of parameters, or {@code null} when there are none.
	 * @return The list of results, or {@code null} when nothing is found.
	 * @throws PersistException
	 *             If the search fails.
	 */
	<T> List<T> getList(final String query, final Map<String, Object> params)
			throws PersistException;
}
