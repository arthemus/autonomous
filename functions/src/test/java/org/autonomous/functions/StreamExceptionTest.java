package org.autonomous.functions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link StreamException} class.
 */
class StreamExceptionTest {

    @Test
    @DisplayName("should create instance with default constructor")
    void shouldCreateInstanceWithDefaultConstructor() {
        // Arrange
        // Act
        StreamException exception = new StreamException();

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isNull();
    }

    @Test
    @DisplayName("should create instance with message")
    void shouldCreateInstanceWithMessage() {
        // Arrange
        // Act
        StreamException exception = new StreamException("stream failed");

        // Assert
        assertThat(exception.getMessage()).isEqualTo("stream failed");
    }

    @Test
    @DisplayName("should be a checked exception")
    void shouldBeACheckedException() {
        // Arrange
        // Act
        // Assert
        assertThat(Exception.class.isAssignableFrom(StreamException.class)).isTrue();
        assertThat(RuntimeException.class.isAssignableFrom(StreamException.class)).isFalse();
    }
}
