package org.autonomous.functions.validators;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link FederativeUnit} enum.
 */
class FederativeUnitTest {

    @Test
    @DisplayName("should contain all twenty-seven Brazilian federative units")
    void shouldContainAllTwentySevenBrazilianFederativeUnits() {
        // Arrange
        // Act
        FederativeUnit[] values = FederativeUnit.values();

        // Assert
        assertThat(values).hasSize(27);
    }

    @Test
    @DisplayName("should return description for Sao Paulo")
    void shouldReturnDescriptionForSaoPaulo() {
        // Arrange
        // Act
        String description = FederativeUnit.SP.getDescription();

        // Assert
        assertThat(description).isEqualTo("Sao Paulo");
    }

    @Test
    @DisplayName("should return description for Distrito Federal")
    void shouldReturnDescriptionForDistritoFederal() {
        // Arrange
        // Act
        String description = FederativeUnit.DF.getDescription();

        // Assert
        assertThat(description).isEqualTo("Distrito Federal");
    }

    @Test
    @DisplayName("should return matching unit when abbreviation is valid")
    void shouldReturnMatchingUnitWhenAbbreviationIsValid() throws Exception {
        // Arrange
        // Act
        FederativeUnit unit = FederativeUnit.fromAbbreviation("SP");

        // Assert
        assertThat(unit).isEqualTo(FederativeUnit.SP);
    }

    @Test
    @DisplayName("should throw when abbreviation is invalid")
    void shouldThrowWhenAbbreviationIsInvalid() {
        // Arrange
        // Act
        // Assert
        assertThatThrownBy(() -> FederativeUnit.fromAbbreviation("XX"))
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Invalid Federative Unit")
                .hasMessageContaining("XX");
    }
}
