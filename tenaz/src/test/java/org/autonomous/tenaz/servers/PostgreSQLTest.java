package org.autonomous.tenaz.servers;

import static org.assertj.core.api.Assertions.assertThat;

import org.autonomous.tenaz.core.AbstractDatabase;
import org.autonomous.tenaz.core.DatabaseConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link PostgreSQL}.
 */
class PostgreSQLTest {

	private PostgreSQL newPostgres() {
		return new PostgreSQL("localhost", "testdb", 5432, "postgres", "secret");
	}

	@Test
	@DisplayName("should return PostgreSQL when getName is called")
	void shouldReturnPostgreSqlWhenGetNameIsCalled() {
		// Arrange
		PostgreSQL postgres = newPostgres();

		// Act
		String name = postgres.getName();

		// Assert
		assertThat(name).isEqualTo("PostgreSQL");
	}

	@Test
	@DisplayName("should return correct JDBC URL when getUrl is called")
	void shouldReturnCorrectJdbcUrlWhenGetUrlIsCalled() {
		// Arrange
		PostgreSQL postgres = newPostgres();

		// Act
		String url = postgres.getUrl();

		// Assert
		assertThat(url).isEqualTo("jdbc:postgresql://localhost:5432/testdb");
	}

	@Test
	@DisplayName("should return PostgreSQL driver when getDriver is called")
	void shouldReturnPostgreSqlDriverWhenGetDriverIsCalled() {
		// Arrange
		PostgreSQL postgres = newPostgres();

		// Act
		String driver = postgres.getDriver();

		// Assert
		assertThat(driver).isEqualTo(PostgreSQL.DRIVER);
		assertThat(driver).isEqualTo("org.postgresql.Driver");
	}

	@Test
	@DisplayName("should be an AbstractDatabase and DatabaseConnection")
	void shouldBeAbstractDatabaseAndDatabaseConnection() {
		// Arrange
		PostgreSQL postgres = newPostgres();

		// Act
		// Assert
		assertThat(postgres).isInstanceOf(AbstractDatabase.class);
		assertThat(postgres).isInstanceOf(DatabaseConnection.class);
	}

	@Test
	@DisplayName("should not leak password in toString")
	void shouldNotLeakPasswordInToString() {
		// Arrange
		PostgreSQL postgres = newPostgres();

		// Act
		String representation = postgres.toString();

		// Assert
		assertThat(representation).doesNotContain("secret");
	}
}
