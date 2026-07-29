package org.autonomous.functions.formatters;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Cpf} formatter.
 */
class CpfTest {

    @Test
    @DisplayName("should return the CPF mask")
    void shouldReturnTheCpfMask() {
        // Arrange
        Cpf cpf = new Cpf();

        // Act
        String mask = cpf.getMask();

        // Assert
        assertThat(mask).isEqualTo("###.###.###-##");
    }

    @Test
    @DisplayName("should format a valid CPF value")
    void shouldFormatValidCpfValue() {
        // Arrange
        ValueFormatter formatter = new ValueFormatter(new Cpf(), "12345678909");

        // Act
        String result = formatter.getFormattedValue();

        // Assert
        assertThat(result).isEqualTo("123.456.789-09");
    }
}
