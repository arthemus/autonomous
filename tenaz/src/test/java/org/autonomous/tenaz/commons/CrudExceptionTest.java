package org.autonomous.tenaz.commons;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link CrudException}.
 */
class CrudExceptionTest {

	@Test
	@DisplayName("should create exception with no-arg constructor")
	void shouldCreateExceptionWithNoArgConstructor() {
		// Arrange
		// Act
		CrudException exception = new CrudException();

		// Assert
		assertThat(exception).isNotNull();
		assertThat(exception.getMessage()).isNull();
	}

	@Test
	@DisplayName("should create exception with message")
	void shouldCreateExceptionWithMessage() {
		// Arrange
		String message = "create failed";

		// Act
		CrudException exception = new CrudException(message);

		// Assert
		assertThat(exception.getMessage()).isEqualTo(message);
	}

	@Test
	@DisplayName("should create exception with cause")
	void shouldCreateExceptionWithCause() {
		// Arrange
		Throwable cause = new RuntimeException("root cause");

		// Act
		CrudException exception = new CrudException(cause);

		// Assert
		assertThat(exception.getCause()).isEqualTo(cause);
	}

	@Test
	@DisplayName("should create exception with message and cause")
	void shouldCreateExceptionWithMessageAndCause() {
		// Arrange
		String message = "create failed";
		Throwable cause = new RuntimeException("root cause");

		// Act
		CrudException exception = new CrudException(message, cause);

		// Assert
		assertThat(exception.getMessage()).isEqualTo(message);
		assertThat(exception.getCause()).isEqualTo(cause);
	}

	@Test
	@DisplayName("should be a checked exception")
	void shouldBeCheckedException() {
		// Arrange
		// Act
		CrudException exception = new CrudException("msg");

		// Assert
		assertThat(exception).isInstanceOf(Exception.class);
	}
}
