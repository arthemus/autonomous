package org.autonomous.tenaz.hibernate;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.autonomous.tenaz.core.PersistException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.google.inject.Inject;

/**
 * 
 * @author arthemus
 * @since 06/09/2013
 * @param <T>
 */
public class SQLHibernateSearch implements HibernateSearch {

	private final Session _session;
	
	@Inject
	public SQLHibernateSearch(Session _session) {	
		this._session = _session;
	}

	@Override	
	public <T> T getUniqueResult(final String query, final Map<String, Object> params) 
			throws PersistException {		
		T object = null;				
		try {
			Query hiQuery = _session.createSQLQuery(query);
			if (params != null) {
				for (Entry<String, Object> item : params.entrySet()) {
					hiQuery.setParameter(item.getKey(), item.getValue());
				}
			}	
			@SuppressWarnings("unchecked")
			List<T> temp = hiQuery.list();
			if (temp != null && !temp.isEmpty())
				object = temp.get(0);
		} catch (Exception e) {
			throw new PersistException("Problemas durante a pesquisa: " + e.getMessage());
		}
		return object;
	}

	@Override
	public <T> List<T> getList(final String query, final Map<String, Object> params) 
			throws PersistException {		
		List<T> list = null;				
		try {
			Query hiQuery = _session.createSQLQuery(query);
			if (params != null) {
				for (Entry<String, Object> item : params.entrySet()) {
					hiQuery.setParameter(item.getKey(), item.getValue());
				}
			}
			@SuppressWarnings("unchecked")
			List<T> temp = hiQuery.list();
			if (temp != null)
				list = temp;
		} catch (Exception e) {
			throw new PersistException("Problemas durante a pesquisa: " + e.getMessage());
		}
		return list;
	}

}
