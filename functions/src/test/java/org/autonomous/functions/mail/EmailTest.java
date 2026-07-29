package org.autonomous.functions.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.apache.commons.mail.EmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for the {@link Email} class.
 *
 * <p>Only the configuration/factory and validation paths are exercised here;
 * the actual sending path is not tested because it requires a live SMTP
 * server.</p>
 */
@ExtendWith(MockitoExtension.class)
class EmailTest {

    @Mock
    private MailService mailService;

    @Test
    @DisplayName("should create a non-null Email instance from a MailService")
    void shouldCreateNonNullEmailInstanceFromMailService() {
        // Arrange
        // Act
        Email email = Email.create(mailService);

        // Assert
        assertThat(email).isNotNull();
    }

    @Test
    @DisplayName("should throw EmailException when a recipient address is invalid")
    void shouldThrowEmailExceptionWhenRecipientAddressIsInvalid() {
        // Arrange
        when(mailService.getSenderEmail()).thenReturn("sender@example.com");
        when(mailService.getSenderName()).thenReturn("Sender");
        when(mailService.getMessage()).thenReturn("Hello");
        when(mailService.getRecipientEmail()).thenReturn("not-a-valid-email");
        Email email = Email.create(mailService);

        // Act
        // Assert
        assertThatThrownBy(email::sendSimple)
                .isInstanceOf(EmailException.class)
                .hasMessageContaining("is not in a valid format");
    }

    @Test
    @DisplayName("should throw EmailException when sending html with an invalid recipient")
    void shouldThrowEmailExceptionWhenSendingHtmlWithInvalidRecipient() {
        // Arrange
        when(mailService.getSenderEmail()).thenReturn("sender@example.com");
        when(mailService.getSenderName()).thenReturn("Sender");
        when(mailService.getMessage()).thenReturn("<p>Hello</p>");
        when(mailService.getRecipientEmail()).thenReturn("invalid-recipient");
        Email email = Email.create(mailService);

        // Act
        // Assert
        assertThatThrownBy(email::sendHtml)
                .isInstanceOf(EmailException.class)
                .hasMessageContaining("is not in a valid format");
    }
}
