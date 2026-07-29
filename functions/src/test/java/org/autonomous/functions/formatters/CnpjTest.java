package org.autonomous.functions.formatters;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Cnpj} formatter.
 */
class CnpjTest {

    @Test
    @DisplayName("should return the CNPJ mask")
    void shouldReturnTheCnpjMask() {
        // Arrange
        Cnpj cnpj = new Cnpj();

        // Act
        String mask = cnpj.getMask();

        // Assert
        assertThat(mask).isEqualTo("##.###.###/####-##");
    }

    @Test
    @DisplayName("should format a valid CNPJ value")
    void shouldFormatValidCnpjValue() {
        // Arrange
        ValueFormatter formatter = new ValueFormatter(new Cnpj(), "12345678000199");

        // Act
        String result = formatter.getFormattedValue();

        // Assert
        assertThat(result).isEqualTo("12.345.678/0001-99");
    }
}
