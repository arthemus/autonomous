package org.autonomous.functions.zipcode;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link ZipCodeServiceFailureException} class.
 */
class ZipCodeServiceFailureExceptionTest {

    @Test
    @DisplayName("should be a runtime exception")
    void shouldBeARuntimeException() {
        // Arrange
        // Act
        // Assert
        assertThat(RuntimeException.class.isAssignableFrom(
                ZipCodeServiceFailureException.class)).isTrue();
    }

    @Test
    @DisplayName("should wrap the provided cause")
    void shouldWrapTheProvidedCause() {
        // Arrange
        Throwable cause = new RuntimeException("connection refused");

        // Act
        ZipCodeServiceFailureException exception =
                new ZipCodeServiceFailureException(cause);

        // Assert
        assertThat(exception.getCause()).isEqualTo(cause);
    }
}
