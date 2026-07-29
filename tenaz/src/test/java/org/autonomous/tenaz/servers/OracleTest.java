package org.autonomous.tenaz.servers;

import static org.assertj.core.api.Assertions.assertThat;

import org.autonomous.tenaz.core.AbstractDatabase;
import org.autonomous.tenaz.core.DatabaseConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Oracle}.
 */
class OracleTest {

	private Oracle newOracle() {
		return new Oracle("localhost", "orcl", 1521, "scott", "tiger");
	}

	@Test
	@DisplayName("should return Oracle when getName is called")
	void shouldReturnOracleWhenGetNameIsCalled() {
		// Arrange
		Oracle oracle = newOracle();

		// Act
		String name = oracle.getName();

		// Assert
		assertThat(name).isEqualTo("Oracle");
	}

	@Test
	@DisplayName("should return correct JDBC URL when getUrl is called")
	void shouldReturnCorrectJdbcUrlWhenGetUrlIsCalled() {
		// Arrange
		Oracle oracle = newOracle();

		// Act
		String url = oracle.getUrl();

		// Assert
		assertThat(url).isEqualTo("jdbc:oracle:thin:@localhost:1521:orcl");
	}

	@Test
	@DisplayName("should return Oracle driver when getDriver is called")
	void shouldReturnOracleDriverWhenGetDriverIsCalled() {
		// Arrange
		Oracle oracle = newOracle();

		// Act
		String driver = oracle.getDriver();

		// Assert
		assertThat(driver).isEqualTo(Oracle.DRIVER);
		assertThat(driver).isEqualTo("oracle.jdbc.driver.OracleDriver");
	}

	@Test
	@DisplayName("should be an AbstractDatabase and DatabaseConnection")
	void shouldBeAbstractDatabaseAndDatabaseConnection() {
		// Arrange
		Oracle oracle = newOracle();

		// Act
		// Assert
		assertThat(oracle).isInstanceOf(AbstractDatabase.class);
		assertThat(oracle).isInstanceOf(DatabaseConnection.class);
	}

	@Test
	@DisplayName("should not leak password in toString")
	void shouldNotLeakPasswordInToString() {
		// Arrange
		Oracle oracle = newOracle();

		// Act
		String representation = oracle.toString();

		// Assert
		assertThat(representation).doesNotContain("tiger");
	}
}
