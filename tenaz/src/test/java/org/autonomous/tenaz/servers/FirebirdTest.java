package org.autonomous.tenaz.servers;

import static org.assertj.core.api.Assertions.assertThat;

import org.autonomous.tenaz.core.AbstractDatabase;
import org.autonomous.tenaz.core.DatabaseConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Firebird}.
 */
class FirebirdTest {

	private Firebird newFirebird() {
		return new Firebird("localhost", "C:/data/db.fdb", 3050, "sysdba", "masterkey");
	}

	@Test
	@DisplayName("should return Firebird when getName is called")
	void shouldReturnFirebirdWhenGetNameIsCalled() {
		// Arrange
		Firebird firebird = newFirebird();

		// Act
		String name = firebird.getName();

		// Assert
		assertThat(name).isEqualTo("Firebird");
	}

	@Test
	@DisplayName("should return correct JDBC URL when getUrl is called")
	void shouldReturnCorrectJdbcUrlWhenGetUrlIsCalled() {
		// Arrange
		Firebird firebird = newFirebird();

		// Act
		String url = firebird.getUrl();

		// Assert
		assertThat(url).isEqualTo("jdbc:firebirdsql:localhost/3050:C:/data/db.fdb");
	}

	@Test
	@DisplayName("should return Firebird driver when getDriver is called")
	void shouldReturnFirebirdDriverWhenGetDriverIsCalled() {
		// Arrange
		Firebird firebird = newFirebird();

		// Act
		String driver = firebird.getDriver();

		// Assert
		assertThat(driver).isEqualTo(Firebird.DRIVER);
		assertThat(driver).isEqualTo("org.firebirdsql.jdbc.FBDriver");
	}

	@Test
	@DisplayName("should be an AbstractDatabase and DatabaseConnection")
	void shouldBeAbstractDatabaseAndDatabaseConnection() {
		// Arrange
		Firebird firebird = newFirebird();

		// Act
		// Assert
		assertThat(firebird).isInstanceOf(AbstractDatabase.class);
		assertThat(firebird).isInstanceOf(DatabaseConnection.class);
	}

	@Test
	@DisplayName("should not leak password in toString")
	void shouldNotLeakPasswordInToString() {
		// Arrange
		Firebird firebird = newFirebird();

		// Act
		String representation = firebird.toString();

		// Assert
		assertThat(representation).doesNotContain("masterkey");
	}
}
