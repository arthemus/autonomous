package org.autonomous.tenaz.core;

import java.io.Serializable;
import java.util.Collection;

/**
 * Base interface for DAO-like objects used by the system, providing the basic
 * features of a persistence layer.
 *
 * @author arthemus
 * @since 26/09/2012
 */
public interface Persist {

	/**
	 * Obtains the entity from the database.
	 *
	 * @param classReference
	 *            The entity class reference.
	 * @param key
	 *            The primary key value.
	 * @return The located entity.
	 * @throws PersistException
	 *             If the read operation fails.
	 */
	<T> T get(final Class<T> classReference, Object key) throws PersistException;

	/**
	 * Obtains a list with 'all' records of the entity in the database.
	 *
	 * @param classReference
	 *            The entity class reference.
	 * @return All records of the entity.
	 * @throws PersistException
	 *             If the list operation fails.
	 */
	<T> Collection<T> list(final Class<T> classReference) throws PersistException;

	/**
	 * Saves the record in the database.
	 *
	 * @param object
	 *            The entity to be saved.
	 * @return This persist instance, for chaining.
	 * @throws PersistException
	 *             If the save operation fails.
	 */
	Persist save(final Serializable object) throws PersistException;

	/**
	 * Saves several records in the database.
	 *
	 * @param objects
	 *            The entities to be saved.
	 * @return This persist instance, for chaining.
	 * @throws PersistException
	 *             If the save operation fails.
	 */
	Persist save(final Collection<?> objects) throws PersistException;

	/**
	 * Updates a single record in the database.
	 *
	 * @param object
	 *            The entity to be updated.
	 * @return This persist instance, for chaining.
	 * @throws PersistException
	 *             If the update operation fails.
	 */
	Persist update(final Serializable object) throws PersistException;

	/**
	 * Updates several records in the database.
	 *
	 * @param objects
	 *            The entities to be updated.
	 * @return This persist instance, for chaining.
	 * @throws PersistException
	 *             If the update operation fails.
	 */
	Persist update(final Collection<?> objects) throws PersistException;

	/**
	 * Removes a single record from the database.
	 *
	 * @param object
	 *            The entity to be removed.
	 * @return This persist instance, for chaining.
	 * @throws PersistException
	 *             If the delete operation fails.
	 */
	Persist delete(final Serializable object) throws PersistException;

	/**
	 * Removes several records from the database.
	 *
	 * @param objects
	 *            The entities to be removed.
	 * @return This persist instance, for chaining.
	 * @throws PersistException
	 *             If the delete operation fails.
	 */
	Persist delete(final Collection<?> objects) throws PersistException;

}
