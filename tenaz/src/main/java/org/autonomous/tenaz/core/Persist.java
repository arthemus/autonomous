package org.autonomous.tenaz.core;

import java.io.Serializable;
import java.util.Collection;

/**
 * Interface base para objetos do tipo DAO utilizados pelo sistema,
 * provendo as funcionalidades básicas de uma persistência. 
 * 
 * @author arthemus
 * @since 26/09/2012
 */
public interface Persist {

	/**
	 * Obtem a entidade do banco de dados.
	 * 
	 * @param classReference
	 * @param key
	 * @return
	 * @throws PersistException
	 */
	<T> T get(final Class<T> classReference, Object key) throws PersistException;

	/**
	 * Obtem uma lista com 'todos' os registro da entidade no banco de dados.
	 * 
	 * @param classReference
	 * @return
	 * @throws PersistException
	 */
	<T> Collection<T> list(final Class<T> classReference) throws PersistException;

	/**
	 * Realiza a gravação do registro no banco de dados.
	 * 
	 * @param object
	 * @return
	 * @throws PersistException
	 */
	Persist save(final Serializable object) throws PersistException;

	/**
	 * Realiza a gravação de vários registros no banco de dados.
	 * 
	 * @param objects
	 * @return
	 * @throws PersistException
	 */
	Persist save(final Collection<?> objects) throws PersistException;

	/**
	 * Atualiza um unico registro no banco de dados.
	 * 
	 * @param object
	 * @return
	 * @throws PersistException
	 */
	Persist update(final Serializable object) throws PersistException;

	/**
	 * Atualiza vários registros no banco de dados.
	 *  
	 * @param objects
	 * @return
	 * @throws PersistException
	 */
	Persist update(final Collection<?> objects) throws PersistException;

	/**
	 * Remove um unico registro do banco de dados.
	 * 
	 * @param object
	 * @return
	 * @throws PersistException
	 */
	Persist delete(final Serializable object) throws PersistException;

	/**
	 * Remove vários registros do banco de dados.
	 * 
	 * @param objects
	 * @return
	 * @throws PersistException
	 */
	Persist delete(final Collection<?> objects) throws PersistException;
	
}
