package org.autonomous.tenaz.hibernate;

import java.util.List;
import java.util.Map;

import org.autonomous.tenaz.core.PersistException;

/**
 * Prove uma forma genéria de consulta no banco de dados utilizando o Hibernate.
 * 
 * Útil para operações comuns e repetitivas entre diferentes entidades.
 * 
 * @author arthemus
 * @since 28/08/2013
 */
public interface HibernateSearch {

	/**
	 * Retorna uma unica entidade resultante da busca no banco de dados.
	 * 
	 * @param query SQL ou NamedQuery
	 * @param params Mapa de parametros, quando não houver, passar 'null'
	 * @return
	 * @throws PersistException
	 */
	<T> T getUniqueResult(final String query, final Map<String, Object> params)
			throws PersistException;

	/**
	 * Retorna uma lista de resultados.
	 * 
	 * @param query SQL ou NamedQuery
	 * @param params Mapa de parametros, quando não houver, passar 'null'
	 * @return
	 * @throws PersistException
	 */
	<T> List<T> getList(final String query, final Map<String, Object> params)
			throws PersistException;
}
