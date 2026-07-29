package org.autonomous.tenaz.hibernate;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.autonomous.tenaz.core.PersistException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link HibernateSearch} contract via a minimal
 * implementation.
 */
class HibernateSearchTest {

	private static final class TestSearch implements HibernateSearch {

		@Override
		public <T> T getUniqueResult(String query, Map<String, Object> params) throws PersistException {
			return null;
		}

		@Override
		public <T> List<T> getList(String query, Map<String, Object> params) throws PersistException {
			return Arrays.asList();
		}
	}

	@Test
	@DisplayName("should return null when getUniqueResult is called on minimal implementation")
	void shouldReturnNullWhenGetUniqueResultIsCalledOnMinimalImplementation() throws Exception {
		// Arrange
		TestSearch search = new TestSearch();

		// Act
		String result = search.getUniqueResult("query", null);

		// Assert
		assertThat(result).isNull();
	}

	@Test
	@DisplayName("should return empty list when getList is called on minimal implementation")
	void shouldReturnEmptyListWhenGetListIsCalledOnMinimalImplementation() throws Exception {
		// Arrange
		TestSearch search = new TestSearch();

		// Act
		List<String> result = search.getList("query", null);

		// Assert
		assertThat(result).isEmpty();
	}
}
