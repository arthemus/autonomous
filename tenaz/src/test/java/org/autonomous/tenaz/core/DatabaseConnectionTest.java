package org.autonomous.tenaz.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Connection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link DatabaseConnection} contract via a minimal
 * implementation.
 */
class DatabaseConnectionTest {

	private static final class TestDatabaseConnection implements DatabaseConnection {

		@Override
		public Connection getConnection() throws ConnectionException {
			throw new ConnectionException("not connected");
		}
	}

	@Test
	@DisplayName("should throw ConnectionException when connection cannot be established")
	void shouldThrowConnectionExceptionWhenConnectionCannotBeEstablished() {
		// Arrange
		TestDatabaseConnection connection = new TestDatabaseConnection();

		// Act
		// Assert
		assertThatThrownBy(() -> connection.getConnection())
				.isInstanceOf(ConnectionException.class)
				.hasMessage("not connected");
	}

	@Test
	@DisplayName("should be assignable from AbstractDatabase")
	void shouldBeAssignableFromAbstractDatabase() {
		// Arrange
		// Act
		boolean result = DatabaseConnection.class.isAssignableFrom(AbstractDatabase.class);

		// Assert
		assertThat(result).isTrue();
	}
}
