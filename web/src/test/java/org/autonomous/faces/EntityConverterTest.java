package org.autonomous.faces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link EntityConverter}.
 */
@DisplayName("EntityConverter")
@ExtendWith(MockitoExtension.class)
class EntityConverterTest {

    @Mock
    private FacesContext context;

    @Mock
    private UIComponent component;

    private EntityConverter converter;

    @BeforeEach
    void setUp() {
        converter = new EntityConverter();
    }

    @Test
    @DisplayName("getAsObject returns null for a null value")
    void getAsObjectReturnsNullForNull() {
        // Arrange
        // Act
        Object result = converter.getAsObject(context, component, null);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("getAsObject returns null for an empty value")
    void getAsObjectReturnsNullForEmpty() {
        // Arrange
        // Act
        Object result = converter.getAsObject(context, component, "");

        // Assert
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("getAsObject returns the entity stored under the given key")
    void getAsObjectReturnsStoredEntity() {
        // Arrange
        Object entity = new Object();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("1", entity);
        when(component.getAttributes()).thenReturn(attributes);

        // Act
        Object result = converter.getAsObject(context, component, "1");

        // Assert
        assertThat(result).isSameAs(entity);
    }

    @Test
    @DisplayName("getAsString stores the entity and returns a sequential key")
    void getAsStringStoresEntityAndReturnsKey() {
        // Arrange
        Map<String, Object> attributes = new HashMap<>();
        when(component.getAttributes()).thenReturn(attributes);
        Object entity = new Object();

        // Act
        String key = converter.getAsString(context, component, entity);

        // Assert
        assertThat(key).isEqualTo("1");
        assertThat(attributes).containsEntry("1", entity);
    }

    @Test
    @DisplayName("getAsString returns an incremented key when attributes already exist")
    void getAsStringReturnsIncrementedKey() {
        // Arrange
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("1", new Object());
        when(component.getAttributes()).thenReturn(attributes);
        Object entity = new Object();

        // Act
        String key = converter.getAsString(context, component, entity);

        // Assert
        assertThat(key).isEqualTo("2");
        assertThat(attributes).containsEntry("2", entity);
    }
}
