package org.autonomous.tenaz.hibernate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.autonomous.tenaz.core.PersistException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.google.inject.Injector;

/**
 * Unit tests for {@link NamedHibernateSearch}.
 */
@ExtendWith(MockitoExtension.class)
class NamedHibernateSearchTest {

	@Mock
	private Injector injector;

	@Mock
	private Session session;

	@Mock
	private Query query;

	private NamedHibernateSearch search;

	@BeforeEach
	void setUp() {
		search = new NamedHibernateSearch(injector);
	}

	@Test
	@DisplayName("should return first result when getUniqueResult succeeds with params")
	void shouldReturnFirstResultWhenGetUniqueResultSucceedsWithParams() throws Exception {
		// Arrange
		Map<String, Object> params = new HashMap<>();
		params.put("name", "test");
		when(injector.getInstance(Session.class)).thenReturn(session);
		when(session.getNamedQuery("findByName")).thenReturn(query);
		when(query.list()).thenReturn(Arrays.asList("first", "second"));

		// Act
		String result = search.getUniqueResult("findByName", params);

		// Assert
		assertThat(result).isEqualTo("first");
		verify(query).setParameter("name", "test");
		verify(session).close();
	}

	@Test
	@DisplayName("should return null when getUniqueResult returns empty list")
	void shouldReturnNullWhenGetUniqueResultReturnsEmptyList() throws Exception {
		// Arrange
		when(injector.getInstance(Session.class)).thenReturn(session);
		when(session.getNamedQuery("findAll")).thenReturn(query);
		when(query.list()).thenReturn(Collections.emptyList());

		// Act
		String result = search.getUniqueResult("findAll", null);

		// Assert
		assertThat(result).isNull();
		verify(session).close();
	}

	@Test
	@DisplayName("should throw PersistException and close session when getUniqueResult fails")
	void shouldThrowPersistExceptionAndCloseSessionWhenGetUniqueResultFails() {
		// Arrange
		when(injector.getInstance(Session.class)).thenReturn(session);
		when(session.getNamedQuery("bad")).thenThrow(new RuntimeException("query failed"));

		// Act
		// Assert
		assertThatThrownBy(() -> search.getUniqueResult("bad", null))
				.isInstanceOf(PersistException.class)
				.hasMessageContaining("query failed");
		verify(session).close();
	}

	@Test
	@DisplayName("should return list when getList succeeds")
	void shouldReturnListWhenGetListSucceeds() throws Exception {
		// Arrange
		when(injector.getInstance(Session.class)).thenReturn(session);
		when(session.getNamedQuery("findAll")).thenReturn(query);
		when(query.list()).thenReturn(Arrays.asList("a", "b"));

		// Act
		List<String> result = search.getList("findAll", null);

		// Assert
		assertThat(result).containsExactly("a", "b");
		verify(session).close();
	}

	@Test
	@DisplayName("should return null when getList query returns null")
	void shouldReturnNullWhenGetListQueryReturnsNull() throws Exception {
		// Arrange
		when(injector.getInstance(Session.class)).thenReturn(session);
		when(session.getNamedQuery("findAll")).thenReturn(query);
		when(query.list()).thenReturn(null);

		// Act
		List<String> result = search.getList("findAll", null);

		// Assert
		assertThat(result).isNull();
		verify(session).close();
	}

	@Test
	@DisplayName("should throw PersistException and close session when getList fails")
	void shouldThrowPersistExceptionAndCloseSessionWhenGetListFails() {
		// Arrange
		when(injector.getInstance(Session.class)).thenReturn(session);
		when(session.getNamedQuery("bad")).thenThrow(new RuntimeException("query failed"));

		// Act
		// Assert
		assertThatThrownBy(() -> search.getList("bad", null))
				.isInstanceOf(PersistException.class)
				.hasMessageContaining("query failed");
		verify(session).close();
	}

	@Test
	@DisplayName("should implement HibernateSearch")
	void shouldImplementHibernateSearch() {
		// Arrange
		// Act
		// Assert
		assertThat(search).isInstanceOf(HibernateSearch.class);
	}
}
