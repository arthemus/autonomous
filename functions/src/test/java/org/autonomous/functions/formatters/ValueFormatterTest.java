package org.autonomous.functions.formatters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for the {@link ValueFormatter} class using a mocked
 * {@link Formatter}.
 */
@ExtendWith(MockitoExtension.class)
class ValueFormatterTest {

    @Mock
    private Formatter formatter;

    @Test
    @DisplayName("should format value using the provided mask")
    void shouldFormatValueUsingProvidedMask() {
        // Arrange
        when(formatter.getMask()).thenReturn("###.###.###-##");
        ValueFormatter valueFormatter = new ValueFormatter(formatter, "12345678909");

        // Act
        String result = valueFormatter.getFormattedValue();

        // Assert
        assertThat(result).isEqualTo("123.456.789-09");
    }

    @Test
    @DisplayName("should pad value with placeholder characters when shorter than mask")
    void shouldPadValueWithPlaceholderCharactersWhenShorterThanMask() {
        // Arrange
        when(formatter.getMask()).thenReturn("###.###.###-##");
        ValueFormatter valueFormatter = new ValueFormatter(formatter, "123");

        // Act
        String result = valueFormatter.getFormattedValue();

        // Assert
        assertThat(result).startsWith("123.").hasSize(14);
    }
}
