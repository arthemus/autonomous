package org.autonomous.tenaz.servers;

import static org.assertj.core.api.Assertions.assertThat;

import org.autonomous.tenaz.core.AbstractDatabase;
import org.autonomous.tenaz.core.DatabaseConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link HSQLDB}.
 */
class HSQLDBTest {

	private HSQLDB newHsqldb() {
		return new HSQLDB("file", "testdb", 9001, "sa", "");
	}

	@Test
	@DisplayName("should return HSQLDB when getName is called")
	void shouldReturnHsqldbWhenGetNameIsCalled() {
		// Arrange
		HSQLDB hsqldb = newHsqldb();

		// Act
		String name = hsqldb.getName();

		// Assert
		assertThat(name).isEqualTo("HSQLDB");
	}

	@Test
	@DisplayName("should return correct JDBC URL when getUrl is called")
	void shouldReturnCorrectJdbcUrlWhenGetUrlIsCalled() {
		// Arrange
		HSQLDB hsqldb = newHsqldb();

		// Act
		String url = hsqldb.getUrl();

		// Assert
		assertThat(url).isEqualTo("jdbc:hsqldb:file:testdb");
	}

	@Test
	@DisplayName("should return HSQLDB driver when getDriver is called")
	void shouldReturnHsqldbDriverWhenGetDriverIsCalled() {
		// Arrange
		HSQLDB hsqldb = newHsqldb();

		// Act
		String driver = hsqldb.getDriver();

		// Assert
		assertThat(driver).isEqualTo("org.hsqldb.jdbcDriver");
	}

	@Test
	@DisplayName("should be an AbstractDatabase and DatabaseConnection")
	void shouldBeAbstractDatabaseAndDatabaseConnection() {
		// Arrange
		HSQLDB hsqldb = newHsqldb();

		// Act
		// Assert
		assertThat(hsqldb).isInstanceOf(AbstractDatabase.class);
		assertThat(hsqldb).isInstanceOf(DatabaseConnection.class);
	}
}
