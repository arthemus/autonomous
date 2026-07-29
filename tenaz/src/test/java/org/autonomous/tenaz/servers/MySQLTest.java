package org.autonomous.tenaz.servers;

import static org.assertj.core.api.Assertions.assertThat;

import org.autonomous.tenaz.core.AbstractDatabase;
import org.autonomous.tenaz.core.DatabaseConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MySQL}.
 */
class MySQLTest {

	private MySQL newMySQL() {
		return new MySQL("localhost", "testdb", 3306, "root", "rootpass");
	}

	@Test
	@DisplayName("should return MySQL when getName is called")
	void shouldReturnMysqlWhenGetNameIsCalled() {
		// Arrange
		MySQL mysql = newMySQL();

		// Act
		String name = mysql.getName();

		// Assert
		assertThat(name).isEqualTo("MySQL");
	}

	@Test
	@DisplayName("should return correct JDBC URL when getUrl is called")
	void shouldReturnCorrectJdbcUrlWhenGetUrlIsCalled() {
		// Arrange
		MySQL mysql = newMySQL();

		// Act
		String url = mysql.getUrl();

		// Assert
		assertThat(url).isEqualTo("jdbc:mysql://localhost:3306/testdb");
	}

	@Test
	@DisplayName("should return MySQL driver when getDriver is called")
	void shouldReturnMysqlDriverWhenGetDriverIsCalled() {
		// Arrange
		MySQL mysql = newMySQL();

		// Act
		String driver = mysql.getDriver();

		// Assert
		assertThat(driver).isEqualTo(MySQL.DRIVER);
		assertThat(driver).isEqualTo("com.mysql.jdbc.Driver");
	}

	@Test
	@DisplayName("should be an AbstractDatabase and DatabaseConnection")
	void shouldBeAbstractDatabaseAndDatabaseConnection() {
		// Arrange
		MySQL mysql = newMySQL();

		// Act
		// Assert
		assertThat(mysql).isInstanceOf(AbstractDatabase.class);
		assertThat(mysql).isInstanceOf(DatabaseConnection.class);
	}

	@Test
	@DisplayName("should not leak password in toString")
	void shouldNotLeakPasswordInToString() {
		// Arrange
		MySQL mysql = newMySQL();

		// Act
		String representation = mysql.toString();

		// Assert
		assertThat(representation).doesNotContain("rootpass");
	}
}
