package org.autonomous.tenaz.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link ConnectionException}.
 */
class ConnectionExceptionTest {

	@Test
	@DisplayName("should create exception with default message when no-arg constructor is used")
	void shouldCreateExceptionWithDefaultMessageWhenNoArgConstructorIsUsed() {
		// Arrange
		// Act
		ConnectionException exception = new ConnectionException();

		// Assert
		assertThat(exception.getMessage()).isEqualTo("Problems connecting to the database");
	}

	@Test
	@DisplayName("should create exception with custom message")
	void shouldCreateExceptionWithCustomMessage() {
		// Arrange
		String message = "connection refused";

		// Act
		ConnectionException exception = new ConnectionException(message);

		// Assert
		assertThat(exception.getMessage()).isEqualTo(message);
	}

	@Test
	@DisplayName("should create exception with message and cause")
	void shouldCreateExceptionWithMessageAndCause() {
		// Arrange
		String message = "connection refused";
		Throwable cause = new RuntimeException("root cause");

		// Act
		ConnectionException exception = new ConnectionException(message, cause);

		// Assert
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getCause()).isEqualTo(cause);
	}

	@Test
	@DisplayName("should create exception with cause only")
	void shouldCreateExceptionWithCauseOnly() {
		// Arrange
		Throwable cause = new RuntimeException("root cause");

		// Act
		ConnectionException exception = new ConnectionException(cause);

		// Assert
		assertThat(exception.getCause()).isEqualTo(cause);
	}
}
