package org.autonomous.functions;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link ExceptionResolver} class.
 */
class ExceptionResolverTest {

    @Test
    @DisplayName("should return a non-null ExceptionResolver when given a throwable")
    void shouldReturnNonNullResolverWhenGivenThrowable() {
        // Arrange
        Throwable throwable = new RuntimeException("some error");

        // Act
        ExceptionResolver resolver = ExceptionResolver.by(throwable);

        // Assert
        assertThat(resolver).isNotNull();
    }

    @Test
    @DisplayName("should return original message when pattern is unknown")
    void shouldReturnOriginalMessageWhenPatternIsUnknown() {
        // Arrange
        Throwable throwable = new RuntimeException("unexpected failure");

        // Act
        String message = ExceptionResolver.by(throwable).getNewMessage();

        // Assert
        assertThat(message).isEqualTo("unexpected failure");
    }

    @Test
    @DisplayName("should return friendly message when file not found pattern is present")
    void shouldReturnFriendlyMessageWhenFileNotFoundPatternIsPresent() {
        // Arrange
        Throwable throwable = new FileNotFoundException(
                "The system cannot find the file specified");

        // Act
        String message = ExceptionResolver.by(throwable).getNewMessage();

        // Assert
        assertThat(message).isEqualTo("The specified file could not be located");
    }

    @Test
    @DisplayName("should return friendly message when foreign key pattern is present")
    void shouldReturnFriendlyMessageWhenForeignKeyPatternIsPresent() {
        // Arrange
        Throwable throwable = new RuntimeException(
                "Cannot delete record: FOREIGN KEY constraint failed");

        // Act
        String message = ExceptionResolver.by(throwable).getNewMessage();

        // Assert
        assertThat(message).isEqualTo(
                "This record is currently in use by the system and cannot be deleted!");
    }
}
