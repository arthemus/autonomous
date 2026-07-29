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
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link SQLHibernateSearch}.
 */
@ExtendWith(MockitoExtension.class)
class SQLHibernateSearchTest {

	@Mock
	private Session session;

	@Mock
	private SQLQuery query;

	private SQLHibernateSearch search;

	@BeforeEach
	void setUp() {
		search = new SQLHibernateSearch(session);
	}

	@Test
	@DisplayName("should return first result when getUniqueResult succeeds with params")
	void shouldReturnFirstResultWhenGetUniqueResultSucceedsWithParams() throws Exception {
		// Arrange
		Map<String, Object> params = new HashMap<>();
		params.put("id", 1);
		when(session.createSQLQuery("SELECT * FROM t WHERE id = :id")).thenReturn(query);
		when(query.list()).thenReturn(Arrays.asList("first", "second"));

		// Act
		String result = search.getUniqueResult("SELECT * FROM t WHERE id = :id", params);

		// Assert
		assertThat(result).isEqualTo("first");
		verify(query).setParameter("id", 1);
	}

	@Test
	@DisplayName("should return null when getUniqueResult returns empty list")
	void shouldReturnNullWhenGetUniqueResultReturnsEmptyList() throws Exception {
		// Arrange
		when(session.createSQLQuery("SELECT * FROM t")).thenReturn(query);
		when(query.list()).thenReturn(Collections.emptyList());

		// Act
		String result = search.getUniqueResult("SELECT * FROM t", null);

		// Assert
		assertThat(result).isNull();
	}

	@Test
	@DisplayName("should throw PersistException when getUniqueResult fails")
	void shouldThrowPersistExceptionWhenGetUniqueResultFails() {
		// Arrange
		when(session.createSQLQuery("BAD")).thenThrow(new RuntimeException("syntax error"));

		// Act
		// Assert
		assertThatThrownBy(() -> search.getUniqueResult("BAD", null))
				.isInstanceOf(PersistException.class)
				.hasMessageContaining("syntax error");
	}

	@Test
	@DisplayName("should return list when getList succeeds")
	void shouldReturnListWhenGetListSucceeds() throws Exception {
		// Arrange
		when(session.createSQLQuery("SELECT * FROM t")).thenReturn(query);
		when(query.list()).thenReturn(Arrays.asList("a", "b"));

		// Act
		List<String> result = search.getList("SELECT * FROM t", null);

		// Assert
		assertThat(result).containsExactly("a", "b");
	}

	@Test
	@DisplayName("should return null when getList query returns null")
	void shouldReturnNullWhenGetListQueryReturnsNull() throws Exception {
		// Arrange
		when(session.createSQLQuery("SELECT * FROM t")).thenReturn(query);
		when(query.list()).thenReturn(null);

		// Act
		List<String> result = search.getList("SELECT * FROM t", null);

		// Assert
		assertThat(result).isNull();
	}

	@Test
	@DisplayName("should throw PersistException when getList fails")
	void shouldThrowPersistExceptionWhenGetListFails() {
		// Arrange
		when(session.createSQLQuery("BAD")).thenThrow(new RuntimeException("syntax error"));

		// Act
		// Assert
		assertThatThrownBy(() -> search.getList("BAD", null))
				.isInstanceOf(PersistException.class)
				.hasMessageContaining("syntax error");
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
