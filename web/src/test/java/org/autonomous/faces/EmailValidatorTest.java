package org.autonomous.faces;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link EmailValidator}.
 */
@DisplayName("EmailValidator")
@ExtendWith(MockitoExtension.class)
class EmailValidatorTest {

    @Mock
    private FacesContext context;

    @Mock
    private UIComponent component;

    private EmailValidator validator;

    @BeforeEach
    void setUp() {
        validator = new EmailValidator();
    }

    @Test
    @DisplayName("validates a simple valid e-mail without throwing")
    void validateAcceptsSimpleValidEmail() {
        // Arrange
        String email = "user@example.com";

        // Act / Assert
        assertThatCode(() -> validator.validate(context, component, email))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("validates a valid e-mail with sub-domains without throwing")
    void validateAcceptsComplexValidEmail() {
        // Arrange
        String email = "user.name@sub.domain.example.com";

        // Act / Assert
        assertThatCode(() -> validator.validate(context, component, email))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("rejects an e-mail without a domain part")
    void validateRejectsEmailWithoutDomain() {
        // Arrange
        String email = "user@";

        // Act / Assert
        assertThatThrownBy(() -> validator.validate(context, component, email))
                .isInstanceOf(ValidatorException.class);
    }

    @Test
    @DisplayName("rejects an e-mail without an at sign")
    void validateRejectsEmailWithoutAtSign() {
        // Arrange
        String email = "userexample.com";

        // Act / Assert
        assertThatThrownBy(() -> validator.validate(context, component, email))
                .isInstanceOf(ValidatorException.class);
    }

    @Test
    @DisplayName("rejects an empty string")
    void validateRejectsEmptyString() {
        // Arrange
        String email = "";

        // Act / Assert
        assertThatThrownBy(() -> validator.validate(context, component, email))
                .isInstanceOf(ValidatorException.class);
    }

    @Test
    @DisplayName("rejects a null value")
    void validateRejectsNull() {
        // Arrange
        // Act / Assert
        assertThatThrownBy(() -> validator.validate(context, component, null))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("the raised exception carries an ERROR severity message")
    void validateRaisesErrorSeverityMessage() {
        // Arrange
        String email = "invalid";

        // Act / Assert
        assertThatThrownBy(() -> validator.validate(context, component, email))
                .isInstanceOfSatisfying(ValidatorException.class, ex -> {
                    FacesMessage msg = ex.getFacesMessage();
                    org.assertj.core.api.Assertions.assertThat(msg).isNotNull();
                    org.assertj.core.api.Assertions.assertThat(msg.getSeverity())
                            .isEqualTo(FacesMessage.SEVERITY_ERROR);
                });
    }
}
