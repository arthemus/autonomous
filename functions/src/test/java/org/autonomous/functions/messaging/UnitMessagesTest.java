package org.autonomous.functions.messaging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.MissingResourceException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link UnitMessages} implementation of {@link Messages}.
 */
class UnitMessagesTest {

    @Test
    @DisplayName("should return message for an existing key")
    void shouldReturnMessageForExistingKey() {
        // Arrange
        UnitMessages messages = new UnitMessages();

        // Act
        String message = messages.getMessage("welcome.message");

        // Assert
        assertThat(message).isEqualTo(
                "Welcome to the autonomous functions module");
    }

    @Test
    @DisplayName("should throw MissingResourceException for a non existing key")
    void shouldThrowMissingResourceExceptionForNonExistingKey() {
        // Arrange
        UnitMessages messages = new UnitMessages();

        // Act
        // Assert
        assertThatThrownBy(() -> messages.getMessage("non.existing.key"))
                .isInstanceOf(MissingResourceException.class);
    }

    @Test
    @DisplayName("should interpolate parameters into the message")
    void shouldInterpolateParametersIntoMessage() {
        // Arrange
        UnitMessages messages = new UnitMessages();

        // Act
        String message = messages.getMessage("greeting.message", "World");

        // Assert
        assertThat(message).isEqualTo("Hello World");
    }

    @Test
    @DisplayName("should be assignable to the Messages interface")
    void shouldBeAssignableFromMessagesInterface() {
        // Arrange
        // Act
        UnitMessages messages = new UnitMessages();

        // Assert
        assertThat(messages).isInstanceOf(Messages.class);
    }
}
