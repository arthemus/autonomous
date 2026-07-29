package org.autonomous.tenaz.servers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.autonomous.tenaz.core.PersistException;
import org.autonomous.tenaz.hibernate.SQLHibernateSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link FirebirdUtils}.
 */
@ExtendWith(MockitoExtension.class)
class FirebirdUtilsTest {

	@Mock
	private SQLHibernateSearch sqlSearch;

	private FirebirdUtils firebirdUtils;

	@BeforeEach
	void setUp() {
		firebirdUtils = new FirebirdUtils(sqlSearch);
	}

	@Test
	@DisplayName("should return trimmed table names when getTables succeeds")
	void shouldReturnTrimmedTableNamesWhenGetTablesSucceeds() throws Exception {
		// Arrange
		List<Object> raw = Arrays.<Object>asList("  CUSTOMERS  ", "  ORDERS  ");
		when(sqlSearch.getList(any(String.class), (Map<String, Object>) isNull())).thenReturn(raw);

		// Act
		List<String> tables = firebirdUtils.getTables();

		// Assert
		assertThat(tables).containsExactly("CUSTOMERS", "ORDERS");
	}

	@Test
	@DisplayName("should return empty list when getTables throws PersistException")
	void shouldReturnEmptyListWhenGetTablesThrowsPersistException() throws Exception {
		// Arrange
		when(sqlSearch.getList(any(String.class), (Map<String, Object>) isNull()))
				.thenThrow(new PersistException("db error"));

		// Act
		List<String> tables = firebirdUtils.getTables();

		// Assert
		assertThat(tables).isEmpty();
	}

	@Test
	@DisplayName("should return empty list when getTables returns empty result")
	void shouldReturnEmptyListWhenGetTablesReturnsEmptyResult() throws Exception {
		// Arrange
		when(sqlSearch.getList(any(String.class), (Map<String, Object>) isNull()))
				.thenReturn(Collections.<Object>emptyList());

		// Act
		List<String> tables = firebirdUtils.getTables();

		// Assert
		assertThat(tables).isEmpty();
	}

	@Test
	@DisplayName("should return trimmed related table names when getRelatedTables succeeds")
	void shouldReturnTrimmedRelatedTableNamesWhenGetRelatedTablesSucceeds() throws Exception {
		// Arrange
		List<Object> raw = Arrays.<Object>asList("  FK_ORDERS  ", "  FK_ITEMS  ");
		when(sqlSearch.getList(any(String.class), anyMap())).thenReturn(raw);

		// Act
		List<String> related = firebirdUtils.getRelatedTables("CUSTOMERS");

		// Assert
		assertThat(related).containsExactly("FK_ORDERS", "FK_ITEMS");
	}

	@Test
	@DisplayName("should return empty list when getRelatedTables throws PersistException")
	void shouldReturnEmptyListWhenGetRelatedTablesThrowsPersistException() throws Exception {
		// Arrange
		when(sqlSearch.getList(any(String.class), anyMap())).thenThrow(new PersistException("db error"));

		// Act
		List<String> related = firebirdUtils.getRelatedTables("CUSTOMERS");

		// Assert
		assertThat(related).isEmpty();
	}

	@Test
	@DisplayName("should return trimmed foreign key constraint names when getForeignKeyConstraints succeeds")
	void shouldReturnTrimmedForeignKeyConstraintNamesWhenGetForeignKeyConstraintsSucceeds() throws Exception {
		// Arrange
		List<Object> raw = Arrays.<Object>asList("  FK_CUST_ORD  ");
		when(sqlSearch.getList(any(String.class), anyMap())).thenReturn(raw);

		// Act
		List<String> constraints = firebirdUtils.getForeignKeyConstraints("ORDERS");

		// Assert
		assertThat(constraints).containsExactly("FK_CUST_ORD");
	}

	@Test
	@DisplayName("should return empty list when getForeignKeyConstraints throws PersistException")
	void shouldReturnEmptyListWhenGetForeignKeyConstraintsThrowsPersistException() throws Exception {
		// Arrange
		when(sqlSearch.getList(any(String.class), anyMap())).thenThrow(new PersistException("db error"));

		// Act
		List<String> constraints = firebirdUtils.getForeignKeyConstraints("ORDERS");

		// Assert
		assertThat(constraints).isEmpty();
	}

	@Test
	@DisplayName("should return empty list when getForeignKeyConstraints returns empty result")
	void shouldReturnEmptyListWhenGetForeignKeyConstraintsReturnsEmptyResult() throws Exception {
		// Arrange
		when(sqlSearch.getList(any(String.class), anyMap()))
				.thenReturn(Collections.<Object>emptyList());

		// Act
		List<String> constraints = firebirdUtils.getForeignKeyConstraints("ORDERS");

		// Assert
		assertThat(constraints).isEmpty();
	}
}
