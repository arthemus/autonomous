package org.autonomous.faces;

import static org.assertj.core.api.Assertions.assertThat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link MaskConverter}.
 */
@DisplayName("MaskConverter")
@ExtendWith(MockitoExtension.class)
class MaskConverterTest {

    @Mock
    private FacesContext context;

    @Mock
    private UIComponent component;

    private MaskConverter converter;

    @BeforeEach
    void setUp() {
        converter = new MaskConverter();
    }

    @Test
    @DisplayName("getAsObject removes special characters from the supplied string")
    void getAsObjectRemovesSpecialCharacters() {
        // Arrange
        String value = "123.456-789";

        // Act
        Object result = converter.getAsObject(context, component, value);

        // Assert
        assertThat(result).isEqualTo("123456789");
    }

    @Test
    @DisplayName("getAsObject returns an empty string for a null input")
    void getAsObjectReturnsEmptyForNull() {
        // Arrange
        // Act
        Object result = converter.getAsObject(context, component, null);

        // Assert
        assertThat(result).isEqualTo("");
    }

    @Test
    @DisplayName("getAsString removes special characters from the supplied value")
    void getAsStringRemovesSpecialCharacters() {
        // Arrange
        // Act
        String result = converter.getAsString(context, component, "AB.CD-EF");

        // Assert
        assertThat(result).isEqualTo("ABCDEF");
    }

    @Test
    @DisplayName("getAsString preserves alphanumeric characters")
    void getAsStringPreservesAlphanumerics() {
        // Arrange
        // Act
        String result = converter.getAsString(context, component, "A1b2C3");

        // Assert
        assertThat(result).isEqualTo("A1b2C3");
    }
}
