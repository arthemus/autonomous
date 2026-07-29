package org.autonomous.tenaz.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link PersistException}.
 */
class PersistExceptionTest {

	@Test
	@DisplayName("should create exception with default message when no-arg constructor is used")
	void shouldCreateExceptionWithDefaultMessageWhenNoArgConstructorIsUsed() {
		// Arrange
		// Act
		PersistException exception = new PersistException();

		// Assert
		assertThat(exception.getMessage()).isEqualTo("Problems during data persistence.");
	}

	@Test
	@DisplayName("should create exception with custom message")
	void shouldCreateExceptionWithCustomMessage() {
		// Arrange
		String message = "could not save record";

		// Act
		PersistException exception = new PersistException(message);

		// Assert
		assertThat(exception.getMessage()).isEqualTo(message);
	}

	@Test
	@DisplayName("should be a SQLException subtype")
	void shouldBeSqlExceptionSubtype() {
		// Arrange
		// Act
		PersistException exception = new PersistException("msg");

		// Assert
		assertThat((Throwable) exception).isInstanceOf(SQLException.class);
	}
}
