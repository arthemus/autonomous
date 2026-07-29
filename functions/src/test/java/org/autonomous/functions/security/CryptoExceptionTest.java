package org.autonomous.functions.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link CryptoException} class.
 */
class CryptoExceptionTest {

    @Test
    @DisplayName("should wrap the provided cause")
    void shouldWrapTheProvidedCause() {
        // Arrange
        Exception cause = new RuntimeException("decrypt failed");

        // Act
        CryptoException exception = new CryptoException(cause);

        // Assert
        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    @DisplayName("should be a checked exception")
    void shouldBeACheckedException() {
        // Arrange
        // Act
        // Assert
        assertThat(Exception.class.isAssignableFrom(CryptoException.class)).isTrue();
        assertThat(RuntimeException.class.isAssignableFrom(CryptoException.class)).isFalse();
    }
}
