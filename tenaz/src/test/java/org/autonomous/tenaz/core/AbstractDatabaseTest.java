package org.autonomous.tenaz.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link AbstractDatabase}.
 */
class AbstractDatabaseTest {

	/**
	 * Minimal concrete subclass used to exercise {@link AbstractDatabase}.
	 */
	private static final class TestDatabase extends AbstractDatabase {

		TestDatabase(String host, String database, int port, String username, String password) {
			super(host, database, port, username, password);
		}

		@Override
		public String getName() {
			return "TestDB";
		}

		@Override
		public String getUrl() {
			return "jdbc:test://" + getHost() + ":" + getPort() + "/" + getDatabase();
		}

		@Override
		public String getDriver() {
			return "org.test.Driver";
		}
	}

	private TestDatabase newDatabase() {
		return new TestDatabase("localhost", "testdb", 1234, "admin", "secret");
	}

	@Test
	@DisplayName("should return host when getHost is called")
	void shouldReturnHostWhenGetHostIsCalled() {
		// Arrange
		TestDatabase db = newDatabase();

		// Act
		String host = db.getHost();

		// Assert
		assertThat(host).isEqualTo("localhost");
	}

	@Test
	@DisplayName("should return database name when getDatabase is called")
	void shouldReturnDatabaseNameWhenGetDatabaseIsCalled() {
		// Arrange
		TestDatabase db = newDatabase();

		// Act
		String database = db.getDatabase();

		// Assert
		assertThat(database).isEqualTo("testdb");
	}

	@Test
	@DisplayName("should return port when getPort is called")
	void shouldReturnPortWhenGetPortIsCalled() {
		// Arrange
		TestDatabase db = newDatabase();

		// Act
		int port = db.getPort();

		// Assert
		assertThat(port).isEqualTo(1234);
	}

	@Test
	@DisplayName("should return username when getUsername is called")
	void shouldReturnUsernameWhenGetUsernameIsCalled() {
		// Arrange
		TestDatabase db = newDatabase();

		// Act
		String username = db.getUsername();

		// Assert
		assertThat(username).isEqualTo("admin");
	}

	@Test
	@DisplayName("should return password when getPassword is called")
	void shouldReturnPasswordWhenGetPasswordIsCalled() {
		// Arrange
		TestDatabase db = newDatabase();

		// Act
		String password = db.getPassword();

		// Assert
		assertThat(password).isEqualTo("secret");
	}

	@Test
	@DisplayName("should return name when getName is called")
	void shouldReturnNameWhenGetNameIsCalled() {
		// Arrange
		TestDatabase db = newDatabase();

		// Act
		String name = db.getName();

		// Assert
		assertThat(name).isEqualTo("TestDB");
	}

	@Test
	@DisplayName("should return url when getUrl is called")
	void shouldReturnUrlWhenGetUrlIsCalled() {
		// Arrange
		TestDatabase db = newDatabase();

		// Act
		String url = db.getUrl();

		// Assert
		assertThat(url).isEqualTo("jdbc:test://localhost:1234/testdb");
	}

	@Test
	@DisplayName("should return driver when getDriver is called")
	void shouldReturnDriverWhenGetDriverIsCalled() {
		// Arrange
		TestDatabase db = newDatabase();

		// Act
		String driver = db.getDriver();

		// Assert
		assertThat(driver).isEqualTo("org.test.Driver");
	}

	@Test
	@DisplayName("should not leak password in toString")
	void shouldNotLeakPasswordInToString() {
		// Arrange
		TestDatabase db = newDatabase();

		// Act
		String representation = db.toString();

		// Assert
		assertThat(representation).doesNotContain("secret");
		assertThat(representation).doesNotContain("password");
	}

	@Test
	@DisplayName("should include host database port and username in toString")
	void shouldIncludeHostDatabasePortAndUsernameInToString() {
		// Arrange
		TestDatabase db = newDatabase();

		// Act
		String representation = db.toString();

		// Assert
		assertThat(representation).contains("localhost");
		assertThat(representation).contains("testdb");
		assertThat(representation).contains("1234");
		assertThat(representation).contains("admin");
	}

	@Test
	@DisplayName("should implement DatabaseConnection")
	void shouldImplementDatabaseConnection() {
		// Arrange
		TestDatabase db = newDatabase();

		// Act
		boolean result = db instanceof DatabaseConnection;

		// Assert
		assertThat(result).isTrue();
	}
}
