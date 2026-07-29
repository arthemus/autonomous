package org.autonomous.faces.messaging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextStub;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link FacesMessages}.
 * <p>
 * {@code ResourceBundle.getString} is final and cannot be stubbed by Mockito,
 * so a real {@link ListResourceBundle} is used as the message bundle.
 */
@DisplayName("FacesMessages")
class FacesMessagesTest {

    @AfterEach
    void tearDown() {
        FacesContextStub.clear();
    }

    @Test
    @DisplayName("getMessage(String) resolves the property from the JSF resource bundle")
    void getMessageResolvesProperty() {
        // Arrange
        FacesContext context = mock(FacesContext.class);
        Application application = mock(Application.class);
        ResourceBundle bundle = new ListResourceBundle() {
            @Override
            protected Object[][] getContents() {
                return new Object[][] { { "greeting", "Hello" } };
            }
        };
        FacesContextStub.setCurrent(context);
        when(context.getApplication()).thenReturn(application);
        when(application.getResourceBundle(context, "msg")).thenReturn(bundle);

        FacesMessages messages = new FacesMessages();

        // Act
        String result = messages.getMessage("greeting");

        // Assert
        assertThat(result).isEqualTo("Hello");
    }

    @Test
    @DisplayName("getMessage(String, Object...) interpolates parameters into the resolved message")
    void getMessageInterpolatesParameters() {
        // Arrange
        FacesContext context = mock(FacesContext.class);
        Application application = mock(Application.class);
        ResourceBundle bundle = new ListResourceBundle() {
            @Override
            protected Object[][] getContents() {
                return new Object[][] { { "welcome", "Welcome {0}, you have {1} messages" } };
            }
        };
        FacesContextStub.setCurrent(context);
        when(context.getApplication()).thenReturn(application);
        when(application.getResourceBundle(context, "msg")).thenReturn(bundle);

        FacesMessages messages = new FacesMessages();

        // Act
        String result = messages.getMessage("welcome", "Alice", 3);

        // Assert
        assertThat(result).isEqualTo("Welcome Alice, you have 3 messages");
    }
}
