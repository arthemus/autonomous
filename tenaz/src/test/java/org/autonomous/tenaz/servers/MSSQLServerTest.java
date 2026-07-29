package org.autonomous.tenaz.servers;

import static org.assertj.core.api.Assertions.assertThat;

import org.autonomous.tenaz.core.AbstractDatabase;
import org.autonomous.tenaz.core.DatabaseConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link MSSQLServer}.
 */
class MSSQLServerTest {

	private MSSQLServer newServer() {
		return new MSSQLServer("localhost", "northwind", 1433, "sa", "password");
	}

	@Test
	@DisplayName("should return Microsoft SQL Server when getName is called")
	void shouldReturnMicrosoftSqlServerWhenGetNameIsCalled() {
		// Arrange
		MSSQLServer server = newServer();

		// Act
		String name = server.getName();

		// Assert
		assertThat(name).isEqualTo("Microsoft SQL Server");
	}

	@Test
	@DisplayName("should return correct JDBC URL when getUrl is called")
	void shouldReturnCorrectJdbcUrlWhenGetUrlIsCalled() {
		// Arrange
		MSSQLServer server = newServer();

		// Act
		String url = server.getUrl();

		// Assert
		assertThat(url).isEqualTo("jdbc:jtds:sqlserver://localhost:1433/northwind");
	}

	@Test
	@DisplayName("should return jtds driver when getDriver is called")
	void shouldReturnJtdsDriverWhenGetDriverIsCalled() {
		// Arrange
		MSSQLServer server = newServer();

		// Act
		String driver = server.getDriver();

		// Assert
		assertThat(driver).isEqualTo(MSSQLServer.DRIVER);
		assertThat(driver).isEqualTo("net.sourceforge.jtds.jdbc.Driver");
	}

	@Test
	@DisplayName("should be an AbstractDatabase and DatabaseConnection")
	void shouldBeAbstractDatabaseAndDatabaseConnection() {
		// Arrange
		MSSQLServer server = newServer();

		// Act
		// Assert
		assertThat(server).isInstanceOf(AbstractDatabase.class);
		assertThat(server).isInstanceOf(DatabaseConnection.class);
	}

	@Test
	@DisplayName("should not leak password in toString")
	void shouldNotLeakPasswordInToString() {
		// Arrange
		MSSQLServer server = newServer();

		// Act
		String representation = server.toString();

		// Assert
		assertThat(representation).doesNotContain("password");
	}
}
