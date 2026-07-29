package org.autonomous.tenaz.hibernate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.autonomous.tenaz.core.PersistException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link HibernatePersist}.
 *
 * <p>
 * Tests use the {@code HibernatePersist(SessionFactory)} constructor so that a
 * mocked {@link SessionFactory} can be injected. The no-arg constructor and
 * {@code getDefault()} rely on classpath configuration files and are not
 * exercised here.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
class HibernatePersistTest {

	@Mock
	private SessionFactory factory;

	@Mock
	private org.hibernate.classic.Session session;

	@Mock
	private org.hibernate.classic.Session currentSession;

	@Mock
	private Transaction transaction;

	@Mock
	private Criteria criteria;

	private HibernatePersist persist;

	@BeforeEach
	void setUp() {
		// openSession() returns org.hibernate.classic.Session in Hibernate 3.6;
		// use doReturn to bypass the stricter type checking of when().
		org.mockito.Mockito.doReturn(session).when(factory).openSession();
		persist = new HibernatePersist(factory);
	}

	@Test
	@DisplayName("should return the session factory when getFactory is called")
	void shouldReturnSessionFactoryWhenGetFactoryIsCalled() {
		// Arrange
		// Act
		SessionFactory result = persist.getFactory();

		// Assert
		assertThat(result).isSameAs(factory);
	}

	@Test
	@DisplayName("should return the session from constructor when getSession is called and it is open")
	void shouldReturnSessionWhenGetSessionIsCalledAndItIsOpen() {
		// Arrange
		when(session.isOpen()).thenReturn(true);

		// Act
		Session result = persist.getSession();

		// Assert
		assertThat(result).isSameAs(session);
	}

	@Test
	@DisplayName("should reopen session when getSession is called and it is closed")
	void shouldReopenSessionWhenGetSessionIsCalledAndItIsClosed() {
		// Arrange
		Session reopened = org.mockito.Mockito.mock(org.hibernate.classic.Session.class);
		when(session.isOpen()).thenReturn(false);
		org.mockito.Mockito.doReturn(reopened).when(factory).openSession();

		// Act
		Session result = persist.getSession();

		// Assert
		assertThat(result).isSameAs(reopened);
	}

	@Test
	@DisplayName("should close session when closeSession is called and it is open")
	void shouldCloseSessionWhenCloseSessionIsCalledAndItIsOpen() {
		// Arrange
		when(session.isOpen()).thenReturn(true);

		// Act
		persist.closeSession();

		// Assert
		verify(session).close();
	}

	@Test
	@DisplayName("should not close session when closeSession is called and it is already closed")
	void shouldNotCloseSessionWhenCloseSessionIsCalledAndItIsAlreadyClosed() {
		// Arrange
		when(session.isOpen()).thenReturn(false);

		// Act
		persist.closeSession();

		// Assert
		org.mockito.Mockito.verify(session, org.mockito.Mockito.never()).close();
	}

	@Test
	@DisplayName("should close factory when closeFactory is called and it is open")
	void shouldCloseFactoryWhenCloseFactoryIsCalledAndItIsOpen() {
		// Arrange
		when(factory.isClosed()).thenReturn(false);

		// Act
		persist.closeFactory();

		// Assert
		verify(factory).close();
	}

	@Test
	@DisplayName("should not close factory when closeFactory is called and it is already closed")
	void shouldNotCloseFactoryWhenCloseFactoryIsCalledAndItIsAlreadyClosed() {
		// Arrange
		when(factory.isClosed()).thenReturn(true);

		// Act
		persist.closeFactory();

		// Assert
		org.mockito.Mockito.verify(factory, org.mockito.Mockito.never()).close();
	}

	@Test
	@DisplayName("should return entity when get succeeds")
	void shouldReturnEntityWhenGetSucceeds() throws Exception {
		// Arrange
		String entity = "found-entity";
		when(session.load(String.class, "key")).thenReturn(entity);

		// Act
		String result = persist.get(String.class, "key");

		// Assert
		assertThat(result).isEqualTo("found-entity");
	}

	@Test
	@DisplayName("should throw PersistException when get fails")
	void shouldThrowPersistExceptionWhenGetFails() {
		// Arrange
		org.mockito.Mockito.doThrow(new RuntimeException("load failed"))
				.when(session).load(any(Class.class), any(Serializable.class));

		// Act
		// Assert
		assertThatThrownBy(() -> persist.get(String.class, "key"))
				.isInstanceOf(PersistException.class)
				.hasMessageContaining("load failed");
	}

	@Test
	@DisplayName("should return list when list succeeds")
	void shouldReturnListWhenListSucceeds() throws Exception {
		// Arrange
		List<String> entities = Arrays.asList("a", "b");
		when(session.createCriteria(String.class)).thenReturn(criteria);
		when(criteria.list()).thenReturn(entities);

		// Act
		Collection<String> result = persist.list(String.class);

		// Assert
		assertThat(result).containsExactly("a", "b");
	}

	@Test
	@DisplayName("should throw PersistException when list fails")
	void shouldThrowPersistExceptionWhenListFails() {
		// Arrange
		when(session.createCriteria(String.class)).thenThrow(new RuntimeException("criteria failed"));

		// Act
		// Assert
		assertThatThrownBy(() -> persist.list(String.class))
				.isInstanceOf(PersistException.class)
				.hasMessageContaining("criteria failed");
	}

	@Test
	@DisplayName("should save entity and return this when save single succeeds")
	void shouldSaveEntityAndReturnThisWhenSaveSingleSucceeds() throws Exception {
		// Arrange
		String entity = "entity";
		org.mockito.Mockito.doReturn(currentSession).when(factory).getCurrentSession();
		when(currentSession.beginTransaction()).thenReturn(transaction);

		// Act
		org.autonomous.tenaz.core.Persist result = persist.save(entity);

		// Assert
		assertThat(result).isSameAs(persist);
		verify(currentSession).merge(entity);
		verify(transaction).commit();
	}

	@Test
	@DisplayName("should throw PersistException when save single fails")
	void shouldThrowPersistExceptionWhenSaveSingleFails() {
		// Arrange
		String entity = "entity";
		org.mockito.Mockito.doReturn(currentSession).when(factory).getCurrentSession();
		when(currentSession.beginTransaction()).thenReturn(transaction);
		doThrow(new RuntimeException("merge failed")).when(currentSession).merge(any());

		// Act
		// Assert
		assertThatThrownBy(() -> persist.save(entity))
				.isInstanceOf(PersistException.class)
				.hasMessageContaining("merge failed");
		verify(transaction).rollback();
	}

	@Test
	@DisplayName("should save collection and return this when save collection succeeds")
	void shouldSaveCollectionAndReturnThisWhenSaveCollectionSucceeds() throws Exception {
		// Arrange
		List<String> entities = Arrays.asList("a", "b");
		org.mockito.Mockito.doReturn(currentSession).when(factory).getCurrentSession();
		when(currentSession.beginTransaction()).thenReturn(transaction);

		// Act
		org.autonomous.tenaz.core.Persist result = persist.save(entities);

		// Assert
		assertThat(result).isSameAs(persist);
		verify(currentSession).merge("a");
		verify(currentSession).merge("b");
		verify(transaction).commit();
	}

	@Test
	@DisplayName("should delegate to save when update single is called")
	void shouldDelegateToSaveWhenUpdateSingleIsCalled() throws Exception {
		// Arrange
		String entity = "entity";
		org.mockito.Mockito.doReturn(currentSession).when(factory).getCurrentSession();
		when(currentSession.beginTransaction()).thenReturn(transaction);

		// Act
		org.autonomous.tenaz.core.Persist result = persist.update(entity);

		// Assert
		assertThat(result).isSameAs(persist);
		verify(currentSession).merge(entity);
	}

	@Test
	@DisplayName("should delegate to save when update collection is called")
	void shouldDelegateToSaveWhenUpdateCollectionIsCalled() throws Exception {
		// Arrange
		List<String> entities = Arrays.asList("a");
		org.mockito.Mockito.doReturn(currentSession).when(factory).getCurrentSession();
		when(currentSession.beginTransaction()).thenReturn(transaction);

		// Act
		org.autonomous.tenaz.core.Persist result = persist.update(entities);

		// Assert
		assertThat(result).isSameAs(persist);
		verify(currentSession).merge("a");
	}

	@Test
	@DisplayName("should delete entity and return this when delete single succeeds")
	void shouldDeleteEntityAndReturnThisWhenDeleteSingleSucceeds() throws Exception {
		// Arrange
		String entity = "entity";
		org.mockito.Mockito.doReturn(currentSession).when(factory).getCurrentSession();
		when(currentSession.beginTransaction()).thenReturn(transaction);

		// Act
		org.autonomous.tenaz.core.Persist result = persist.delete(entity);

		// Assert
		assertThat(result).isSameAs(persist);
		verify(currentSession).delete((Object) entity);
		verify(transaction).commit();
	}

	@Test
	@DisplayName("should throw PersistException when delete single fails")
	void shouldThrowPersistExceptionWhenDeleteSingleFails() {
		// Arrange
		String entity = "entity";
		org.mockito.Mockito.doReturn(currentSession).when(factory).getCurrentSession();
		when(currentSession.beginTransaction()).thenReturn(transaction);
		doThrow(new RuntimeException("delete failed")).when(currentSession).delete(any(Object.class));

		// Act
		// Assert
		assertThatThrownBy(() -> persist.delete(entity))
				.isInstanceOf(PersistException.class)
				.hasMessageContaining("delete failed");
		verify(transaction).rollback();
	}

	@Test
	@DisplayName("should delete collection and return this when delete collection succeeds")
	void shouldDeleteCollectionAndReturnThisWhenDeleteCollectionSucceeds() throws Exception {
		// Arrange
		List<String> entities = Arrays.asList("a", "b");
		org.mockito.Mockito.doReturn(currentSession).when(factory).getCurrentSession();
		when(currentSession.beginTransaction()).thenReturn(transaction);

		// Act
		org.autonomous.tenaz.core.Persist result = persist.delete(entities);

		// Assert
		assertThat(result).isSameAs(persist);
		verify(currentSession).delete((Object) "a");
		verify(currentSession).delete((Object) "b");
		verify(transaction).commit();
	}

	@Test
	@DisplayName("should throw PersistException when delete collection fails")
	void shouldThrowPersistExceptionWhenDeleteCollectionFails() {
		// Arrange
		List<String> entities = Collections.singletonList("a");
		org.mockito.Mockito.doReturn(currentSession).when(factory).getCurrentSession();
		when(currentSession.beginTransaction()).thenReturn(transaction);
		doThrow(new RuntimeException("delete failed")).when(currentSession).delete(any(Object.class));

		// Act
		// Assert
		assertThatThrownBy(() -> persist.delete(entities))
				.isInstanceOf(PersistException.class)
				.hasMessageContaining("delete failed");
		verify(transaction).rollback();
	}

	@Test
	@DisplayName("should implement Persist interface")
	void shouldImplementPersistInterface() {
		// Arrange
		// Act
		// Assert
		assertThat(persist).isInstanceOf(org.autonomous.tenaz.core.Persist.class);
	}
}
