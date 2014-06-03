package org.autonomous.tenaz.hibernate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.autonomous.tenaz.core.Persist;
import org.autonomous.tenaz.core.PersistException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.impl.SessionImpl;

/**
 * <pre>
 * Implementação de persistência de dados utilizando Hibernate. Encapsula as
 * principais ações a serem tomadas com uma entidade como pesquisa, gravação,
 * atualização e exclusão.
 * </pre>
 * 
 * @author arthemus
 * @since 26/09/2012
 * @see Persist
 */
public final class HibernatePersist implements Persist {

	private static final Logger _logger = Logger.getLogger(HibernatePersist.class.getName());
	
	private static SessionFactory factory;

	private static Session session;

	static {
		loadFactory();
		loadSession();
	}

	public static void closeFactory() {
		try {
			if (factory != null && !factory.isClosed())
				factory.close();
		} catch (Exception e) {
			// Not need error catch when closed
		}
	}

	public static void closeSession() {
		SessionImpl sess = (SessionImpl) session;
		try {
			if (sess != null && !sess.isClosed())
				session.close();
		} catch (Exception e) {
			// Not need error catch when closed
		}
	}

	public static SessionFactory getFactory() {
		return factory;
	}

	public static Session getSession() {
		SessionImpl sess = (SessionImpl) session;
		if (sess == null || sess.isClosed())
			loadSession();
		return session;
	}

	private static void loadFactory() {
		if (factory == null) {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");		
			Properties propExt = new Properties();
			try {
				File fileExt = new File("db.properties");
				if (fileExt.exists()) {
					propExt.load(new FileInputStream(fileExt));
					configuration.setProperties(propExt);
				}								
			} catch (FileNotFoundException e) {				
				_logger.info("Error: " + e.getMessage());
			} catch (IOException e) {
				_logger.info("Error: " + e.getMessage());
			}			
			factory = configuration.buildSessionFactory();
		}
	}

	private static void loadSession() {
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
			if (trans != null)
				trans.rollback();
			StringBuilder msg = new StringBuilder(e.getMessage().length() * 2);
			msg.append("Não foi possível gravar o(s) registro(s)!");
			msg.append("\nErro: " + e.getLocalizedMessage());
			if (e.getCause() != null)
				msg.append("\nSQL Erro: " + e.getCause().getMessage());
			throw new PersistException(msg.toString());
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
			if (trans != null)
				trans.rollback();
			StringBuilder msg = new StringBuilder(e.getMessage().length() * 2);
			msg.append("Não foi possível deletar o(s) registro(s)!");
			msg.append("\nErro: " + e.getLocalizedMessage());
			if (e.getCause() != null)
				msg.append("\nSQL Erro: " + e.getCause().getMessage());
			throw new PersistException(msg.toString());
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> reference, Object key) throws PersistException {
		try {
			return (T) session.load(reference, (Serializable) key);	
		} catch (Exception e) {			
			throw new PersistException("Problemas durante a busca ou nenhum registro encontrado."
					+ "\nErro: " + e.getLocalizedMessage() 
					+ "\nSQL Erro: " + e.getCause().getMessage());
		}		
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Collection<T> list(Class<T> reference) throws PersistException {
		try {
			return session.createCriteria(reference).list();
		} catch (Exception e) {
			throw new PersistException("Problemas durante a listagem ou nenhum registro encontrado."
					+ "\nErro: " + e.getLocalizedMessage() 
					+ "\nSQL Erro: " + e.getCause().getMessage());
		}	
	}

	@Override
	public Persist save(Serializable object) throws PersistException {
		LinkedList<Object> listObjects = new LinkedList<Object>();
		listObjects.add(object);
		this.saveOrUpdate(listObjects);
		return this;
	}

	@Override
	public Persist save(Collection<?> objects) throws PersistException {
		LinkedList<Object> listObjects = new LinkedList<Object>(objects);
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
		LinkedList<Object> listObjects = new LinkedList<Object>();
		listObjects.add(object);
		this.removeAll(listObjects);
		return this;
	}
	
	@Override
	public Persist delete(Collection<?> objects) throws PersistException {
		LinkedList<Object> listObjects = new LinkedList<Object>(objects);
		this.removeAll(listObjects);
		return this;
	}

}
