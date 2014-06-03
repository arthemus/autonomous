package org.autonomous.tenaz.core;

import java.io.Serializable;

import org.autonomous.tenaz.hibernate.HibernatePersist;
import org.autonomous.tenaz.hibernate.HibernateSearch;
import org.autonomous.tenaz.hibernate.NamedHibernateSearch;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * Módulo de configuração para as classes injetadas pelo Guice.
 * 
 * @author arthemus
 * @since 20/08/2013
 */
public class PersistenceModule extends AbstractModule implements Serializable {

	private static final long serialVersionUID = 3656216024737253746L;

	@Override
	protected void configure() {
		bind(Persist.class).to(HibernatePersist.class);
		bind(HibernateSearch.class).to(NamedHibernateSearch.class);
	}

	@Provides Session provideSession() {
		SessionFactory factory = HibernatePersist.getFactory();
		Session session = factory.openSession();
		return session;
	}

}
