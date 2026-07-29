package org.autonomous.tenaz.hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.autonomous.tenaz.core.Persist;
import org.autonomous.tenaz.core.PersistException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Data persistence implementation using Hibernate. It encapsulates the main
 * actions to be taken with an entity such as search, save, update and delete.
 * </pre>
 *
 * <p>
 * This class is instance-based: it holds its own {@link SessionFactory} and
 * {@link Session}. A shared default instance is available through
 * {@link #getDefault()} for legacy callers that previously relied on the static
 * factory/session (e.g. servlet filters and JSF listeners).
 * </p>
 *
 * @author arthemus
 * @since 26/09/2012
 * @see Persist
 */
public final class HibernatePersist implements Persist {

	private static final Logger LOGGER = LoggerFactory.getLogger(HibernatePersist.class);

	/**
	 * Singleton holder for the shared default instance. The instance is created
	 * lazily on first access, mirroring the previous static initialization
	 * behavior without relying on static mutable state.
	 */
	private static final class Holder {
		static final HibernatePersist DEFAULT = new HibernatePersist();
	}

	/**
	 * Returns the shared default instance, creating it lazily.
	 *
	 * @return The shared default {@code HibernatePersist} instance.
	 */
	public static HibernatePersist getDefault() {
		return Holder.DEFAULT;
	}

	private SessionFactory factory;

	private Session session;

	/**
	 * Creates a new instance that loads its configuration internally from
	 * {@code hibernate.cfg.xml}, optionally overridden by a
	 * {@code db.properties} classpath resource.
	 */
	public HibernatePersist() {
		loadFactory();
		loadSession();
	}

	/**
	 * Creates a new instance backed by the supplied session factory.
	 *
	 * @param factory
	 *            The Hibernate session factory to use.
	 */
	public HibernatePersist(SessionFactory factory) {
		this.factory = factory;
		this.session = factory.openSession();
	}

	/**
	 * Closes the session factory if it is still open.
	 */
	public void closeFactory() {
		try {
			if (factory != null && !factory.isClosed()) {
				factory.close();
			}
		} catch (Exception e) {
			LOGGER.debug("Ignoring error while closing the session factory", e);
		}
	}

	/**
	 * Closes the current session if it is still open.
	 */
	public void closeSession() {
		try {
			if (session != null && session.isOpen()) {
				session.close();
			}
		} catch (Exception e) {
			LOGGER.debug("Ignoring error while closing the session", e);
		}
	}

	/**
	 * Returns the session factory held by this instance.
	 *
	 * @return The session factory.
	 */
	public SessionFactory getFactory() {
		return factory;
	}

	/**
	 * Returns the current long-lived session, reopening it when it has been
	 * closed.
	 *
	 * @return An open session.
	 */
	public Session getSession() {
		if (session == null || !session.isOpen()) {
			loadSession();
		}
		return session;
	}

	private void loadFactory() {
		if (factory == null) {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			Properties external = new Properties();
			InputStream stream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("db.properties");
			if (stream != null) {
				try {
					external.load(stream);
					// Merge instead of replace so hibernate.cfg.xml defaults are
					// preserved and only overridden by the external properties.
					configuration.addProperties(external);
				} catch (IOException e) {
					LOGGER.warn("Could not read db.properties from the classpath", e);
				} finally {
					try {
						stream.close();
					} catch (IOException e) {
						LOGGER.debug("Could not close db.properties stream", e);
					}
				}
			}
			factory = configuration.buildSessionFactory();
		}
	}

	private void loadSession() {
		session = factory.openSession();
	}

	private void saveOrUpdate(List<Object> listObjectsPersist)
			throws PersistException {
		Session sess = factory.getCurrentSession();
		Transaction trans = sess.beginTransaction();
		try {
			sess.clear();
			int length = listObjectsPersist.size();
			for (int count = 0; count < length; count++) {
				Object item = listObjectsPersist.get(count);
				sess.merge(item);
				if (count % 20 == 0) {
					sess.flush();
					sess.clear();
				}
			}
			sess.flush();
			trans.commit();
		} catch (Exception e) {
			if (trans != null) {
				trans.rollback();
			}
            StringBuilder msg = new StringBuilder();
			msg.append("Could not save the record(s)!");
			msg.append("\nError: ").append(e.getLocalizedMessage());
			if (e.getCause() != null) {
				msg.append("\nSQL Error: ").append(e.getCause().getMessage());
			}
			PersistException persistException = new PersistException(msg.toString());
			persistException.initCause(e);
			throw persistException;
		}
	}

	private void removeAll(List<Object> listObjectsDelete)
			throws PersistException {
		Session sess = factory.getCurrentSession();
		Transaction trans = sess.beginTransaction();
		try {
			sess.clear();
			int length = listObjectsDelete.size();
			for (int count = 0; count < length; count++) {
				Object item = listObjectsDelete.get(count);
				sess.delete(item);
				if (count % 20 == 0) {
					sess.flush();
					sess.clear();
				}
			}
			sess.flush();
			trans.commit();
		} catch (Exception e) {
			if (trans != null) {
				trans.rollback();
			}
			StringBuilder msg = new StringBuilder();
			msg.append("Could not delete the record(s)!");
			msg.append("\nError: ").append(e.getLocalizedMessage());
			if (e.getCause() != null) {
				msg.append("\nSQL Error: ").append(e.getCause().getMessage());
			}
			PersistException persistException = new PersistException(msg.toString());
			persistException.initCause(e);
			throw persistException;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> reference, Object key) throws PersistException {
		try {
			return (T) session.load(reference, (Serializable) key);
		} catch (Exception e) {
			StringBuilder msg = new StringBuilder();
			msg.append("Problems during the search or no record found.");
			msg.append("\nError: ").append(e.getLocalizedMessage());
			Throwable cause = e.getCause();
			if (cause != null) {
				msg.append("\nSQL Error: ").append(cause.getMessage());
			}
			PersistException persistException = new PersistException(msg.toString());
			persistException.initCause(e);
			throw persistException;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Collection<T> list(Class<T> reference) throws PersistException {
		try {
			return session.createCriteria(reference).list();
		} catch (Exception e) {
			StringBuilder msg = new StringBuilder();
			msg.append("Problems during the listing or no record found.");
			msg.append("\nError: ").append(e.getLocalizedMessage());
			Throwable cause = e.getCause();
			if (cause != null) {
				msg.append("\nSQL Error: ").append(cause.getMessage());
			}
			PersistException persistException = new PersistException(msg.toString());
			persistException.initCause(e);
			throw persistException;
		}
	}

	@Override
	public Persist save(Serializable object) throws PersistException {
		LinkedList<Object> listObjects = new LinkedList<>();
		listObjects.add(object);
		this.saveOrUpdate(listObjects);
		return this;
	}

	@Override
	public Persist save(Collection<?> objects) throws PersistException {
		LinkedList<Object> listObjects = new LinkedList<>(objects);
		this.saveOrUpdate(listObjects);
		return this;
	}

	@Override
	public Persist update(Serializable object) throws PersistException {
		this.save(object);
		return this;
	}

	@Override
	public Persist update(Collection<?> objects) throws PersistException {
		this.save(objects);
		return this;
	}

	@Override
	public Persist delete(Serializable object) throws PersistException {
		LinkedList<Object> listObjects = new LinkedList<>();
		listObjects.add(object);
		this.removeAll(listObjects);
		return this;
	}

	@Override
	public Persist delete(Collection<?> objects) throws PersistException {
		LinkedList<Object> listObjects = new LinkedList<>(objects);
		this.removeAll(listObjects);
		return this;
	}

}
