package org.autonomous.tenaz.hibernate;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.autonomous.tenaz.core.PersistException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Implementation of queries using NamedQueries.
 *
 * <p>
 * Instead of building a new Guice injector on every method call, the injector
 * is provided once through the constructor.
 * </p>
 *
 * @author arthemus
 * @since 28/08/2013
 */
public class NamedHibernateSearch implements HibernateSearch {

	private final Injector injector;

	@Inject
	public NamedHibernateSearch(Injector injector) {
		this.injector = injector;
	}

	@Override
	public <T> T getUniqueResult(final String query, final Map<String, Object> params)
			throws PersistException {
		Session session = injector.getInstance(Session.class);
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
			if (temp != null && !temp.isEmpty()) {
				object = temp.get(0);
			}
		} catch (Exception e) {
			PersistException persistException = new PersistException("Problems during the search: " + e.getMessage());
			persistException.initCause(e);
			throw persistException;
		} finally {
			session.close();
		}
		return object;
	}

	@Override
	public <T> List<T> getList(final String query, final Map<String, Object> params)
			throws PersistException {
		Session session = injector.getInstance(Session.class);
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
			if (temp != null) {
				list = temp;
			}
		} catch (Exception e) {
			PersistException persistException = new PersistException("Problems during the search: " + e.getMessage());
			persistException.initCause(e);
			throw persistException;
		} finally {
			session.close();
		}
		return list;
	}

}
