package org.autonomous.functions.zipcode;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link ZipCodeNotFoundException} class.
 */
class ZipCodeNotFoundExceptionTest {

    @Test
    @DisplayName("should be a runtime exception")
    void shouldBeARuntimeException() {
        // Arrange
        // Act
        // Assert
        assertThat(RuntimeException.class.isAssignableFrom(
                ZipCodeNotFoundException.class)).isTrue();
    }

    @Test
    @DisplayName("should include zip code and cause in message when built from int")
    void shouldIncludeZipCodeAndCauseInMessageWhenBuiltFromInt() {
        // Arrange
        Throwable cause = new RuntimeException("service down");

        // Act
        ZipCodeNotFoundException exception =
                new ZipCodeNotFoundException(13084440, cause);

        // Assert
        assertThat(exception.getMessage()).isEqualTo(
                "Zip code 13084440 was not found.");
        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    @DisplayName("should include query and cause in message when built from string")
    void shouldIncludeQueryAndCauseInMessageWhenBuiltFromString() {
        // Arrange
        Throwable cause = new RuntimeException("service down");

        // Act
        ZipCodeNotFoundException exception =
                new ZipCodeNotFoundException("Rua das Flores", cause);

        // Assert
        assertThat(exception.getMessage()).isEqualTo(
                "Search for 'Rua das Flores' returned no results.");
        assertThat(exception.getCause()).isEqualTo(cause);
    }
}
