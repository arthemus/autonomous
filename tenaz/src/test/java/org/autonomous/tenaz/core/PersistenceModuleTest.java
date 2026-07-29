package org.autonomous.tenaz.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.autonomous.tenaz.hibernate.HibernatePersist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PersistenceModuleTest {

	@Mock
	private SessionFactory factory;

	@Mock
	private org.hibernate.classic.Session session;

	private HibernatePersist persist;

	@BeforeEach
	void setUp() {
		org.mockito.Mockito.doReturn(session).when(factory).openSession();
		persist = new HibernatePersist(factory);
	}

	@Test
	@DisplayName("should be a Guice AbstractModule")
	void shouldBeGuiceAbstractModule() {
		// Arrange
		// Act
		PersistenceModule module = new PersistenceModule();

		// Assert
		assertThat(module).isInstanceOf(com.google.inject.AbstractModule.class);
	}

	@Test
	@DisplayName("should open a session from the persist factory when provideSession is called")
	void shouldOpenSessionFromFactoryWhenProvideSessionIsCalled() {
		// Arrange
		PersistenceModule module = new PersistenceModule();

		// Act
		Session result = module.provideSession(persist);

		// Assert
		assertThat(result).isSameAs(session);
	}
}
