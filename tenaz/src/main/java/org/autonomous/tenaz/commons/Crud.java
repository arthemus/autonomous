package org.autonomous.tenaz.commons;

import java.util.Collection;


/**
 * Patterns for a class responsible for the registration of a given entity.
 *
 * These operations can be complemented by a specific persistence system in
 * order to delegate the responsibility of communicating with the database,
 * providing only a common path between the Control and Service/Business layers
 * (Bean -> Services).
 *
 * @author arthemus
 * @since 06/11/2013
 */
public interface Crud<T extends PersistentEntity> {

	/**
	 * Performs the save operation of the entity in the database.
	 *
	 * @param object
	 *            The entity to be saved.
	 * @return A return message from the operation, a success confirmation.
	 * @throws CrudException
	 *             If the save operation fails.
	 */
	String doCreate(final T object) throws CrudException;

	/**
	 * Fetches the record of a given entity.
	 *
	 * @param classReference
	 *            The entity class reference.
	 * @param key
	 *            The primary key value.
	 * @return The located entity.
	 * @throws CrudException
	 *             If the read operation fails.
	 */
	T getRead(final Class<T> classReference, final Object key) throws CrudException;

	/**
	 * Obtains a list with all the records of the entity in the database.
	 *
	 * @param classReference
	 *            The entity class reference.
	 * @return All records of the entity.
	 * @throws CrudException
	 *             If the list operation fails.
	 */
	Collection<T> getList(final Class<T> classReference) throws CrudException;

	/**
	 * Performs the update of the entity in the database.
	 *
	 * @param object
	 *            The entity to be updated.
	 * @return A return message from the operation, a success confirmation.
	 * @throws CrudException
	 *             If the update operation fails.
	 */
	String doUpdate(final T object) throws CrudException;

	/**
	 * Performs the deletion of the entity in the database.
	 *
	 * @param object
	 *            The entity to be deleted.
	 * @return A return message from the operation, a success confirmation.
	 * @throws CrudException
	 *             If the delete operation fails.
	 */
	String doDelete(final T object) throws CrudException;

	/**
	 * Performs the deletion of several records in the database.
	 *
	 * @param listObject
	 *            The entities to be deleted.
	 * @return A return message from the operation, a success confirmation.
	 * @throws CrudException
	 *             If the delete operation fails.
	 */
	String doDelete(final Collection<T> listObject) throws CrudException;

	/**
	 * Prints all records or a specific report of the module.
	 *
	 * @throws CrudException
	 *             If the print operation fails.
	 */
	void doPrint() throws CrudException;

	/**
	 * Obtains a new reference of the entity to be worked on.
	 *
	 * @return A new entity instance.
	 */
	T getNewInstance();
}
