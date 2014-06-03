package org.autonomous.tenaz.hibernate;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.autonomous.tenaz.core.PersistException;
import org.autonomous.tenaz.core.PersistenceModule;
import org.hibernate.Query;
import org.hibernate.Session;

import com.google.inject.Guice;

/**
 * 
 * A implementação de consultas utilizando NamedQuerys.
 * 
 * @author arthemus
 * @since 28/08/2013
 */
public class NamedHibernateSearch implements HibernateSearch {

	@Override	
	public <T> T getUniqueResult(final String query, final Map<String, Object> params) 
			throws PersistException {
		Session session = Guice.createInjector(new PersistenceModule()).getInstance(Session.class);
		T object = null;				
		try {			
			Query hiQuery = session.getNamedQuery(query);
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
		} finally {
			session.close();
		}
		return object;
	}

	@Override
	public <T> List<T> getList(final String query, final Map<String, Object> params) 
			throws PersistException {
		Session session = Guice.createInjector(new PersistenceModule()).getInstance(Session.class);
		List<T> list = null;				
		try {
			Query hiQuery = session.getNamedQuery(query);
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
		} finally {
			session.close();
		}
		return list;
	}
	
}
