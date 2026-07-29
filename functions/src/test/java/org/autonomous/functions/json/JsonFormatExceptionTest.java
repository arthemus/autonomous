package org.autonomous.functions.json;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link JsonFormatException} class.
 */
class JsonFormatExceptionTest {

    @Test
    @DisplayName("should create instance with default constructor")
    void shouldCreateInstanceWithDefaultConstructor() {
        // Arrange
        // Act
        JsonFormatException exception = new JsonFormatException();

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isNull();
    }

    @Test
    @DisplayName("should create instance with message")
    void shouldCreateInstanceWithMessage() {
        // Arrange
        // Act
        JsonFormatException exception = new JsonFormatException("invalid json");

        // Assert
        assertThat(exception.getMessage()).isEqualTo("invalid json");
    }

    @Test
    @DisplayName("should create instance with cause")
    void shouldCreateInstanceWithCause() {
        // Arrange
        Throwable cause = new RuntimeException("parse error");

        // Act
        JsonFormatException exception = new JsonFormatException(cause);

        // Assert
        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    @DisplayName("should be a checked exception")
    void shouldBeACheckedException() {
        // Arrange
        // Act
        // Assert
        assertThat(Exception.class.isAssignableFrom(JsonFormatException.class)).isTrue();
        assertThat(RuntimeException.class.isAssignableFrom(JsonFormatException.class)).isFalse();
    }
}
