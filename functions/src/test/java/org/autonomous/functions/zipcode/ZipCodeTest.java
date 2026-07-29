package org.autonomous.functions.zipcode;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link ZipCode} value object.
 */
class ZipCodeTest {

    private static final String NUMBER = "13084440";
    private static final String LOGRADOURO = "Rua Flordalisa Amalia Grigol Coghi";
    private static final String BAIRRO = "Jardim America";
    private static final String LOCALITY = "Campinas";
    private static final String UF = "SP";

    private ZipCode newZipCode() {
        return new ZipCode(NUMBER, LOGRADOURO, BAIRRO, LOCALITY, UF);
    }

    @Test
    @DisplayName("should return the number passed to the constructor")
    void shouldReturnTheNumberPassedToTheConstructor() {
        // Arrange
        ZipCode zipCode = newZipCode();

        // Act
        String number = zipCode.getNumber();

        // Assert
        assertThat(number).isEqualTo(NUMBER);
    }

    @Test
    @DisplayName("should return the logradouro passed to the constructor")
    void shouldReturnTheLogradouroPassedToTheConstructor() {
        // Arrange
        ZipCode zipCode = newZipCode();

        // Act
        String logradouro = zipCode.getLogradouro();

        // Assert
        assertThat(logradouro).isEqualTo(LOGRADOURO);
    }

    @Test
    @DisplayName("should return the bairro passed to the constructor")
    void shouldReturnTheBairroPassedToTheConstructor() {
        // Arrange
        ZipCode zipCode = newZipCode();

        // Act
        String bairro = zipCode.getBairro();

        // Assert
        assertThat(bairro).isEqualTo(BAIRRO);
    }

    @Test
    @DisplayName("should return the locality passed to the constructor")
    void shouldReturnTheLocalityPassedToTheConstructor() {
        // Arrange
        ZipCode zipCode = newZipCode();

        // Act
        String locality = zipCode.getLocality();

        // Assert
        assertThat(locality).isEqualTo(LOCALITY);
    }

    @Test
    @DisplayName("should return the uf passed to the constructor")
    void shouldReturnTheUfPassedToTheConstructor() {
        // Arrange
        ZipCode zipCode = newZipCode();

        // Act
        String uf = zipCode.getUf();

        // Assert
        assertThat(uf).isEqualTo(UF);
    }

    @Test
    @DisplayName("should be equal to another zip code with the same values")
    void shouldBeEqualToAnotherZipCodeWithSameValues() {
        // Arrange
        ZipCode one = newZipCode();
        ZipCode two = newZipCode();

        // Act
        // Assert
        assertThat(one).isEqualTo(two);
    }

    @Test
    @DisplayName("should not be equal to a zip code with different values")
    void shouldNotBeEqualToZipCodeWithDifferentValues() {
        // Arrange
        ZipCode one = newZipCode();
        ZipCode two = new ZipCode("17065340", LOGRADOURO, BAIRRO, LOCALITY, UF);

        // Act
        // Assert
        assertThat(one).isNotEqualTo(two);
    }

    @Test
    @DisplayName("should not be equal to null")
    void shouldNotBeEqualToNull() {
        // Arrange
        ZipCode zipCode = newZipCode();

        // Act
        // Assert
        assertThat(zipCode).isNotEqualTo(null);
    }

    @Test
    @DisplayName("should not be equal to a different type")
    void shouldNotBeEqualToDifferentType() {
        // Arrange
        ZipCode zipCode = newZipCode();

        // Act
        // Assert
        assertThat(zipCode).isNotEqualTo("a string");
    }

    @Test
    @DisplayName("should be equal to itself")
    void shouldBeEqualToItself() {
        // Arrange
        ZipCode zipCode = newZipCode();

        // Act
        // Assert
        assertThat(zipCode).isEqualTo(zipCode);
    }

    @Test
    @DisplayName("should have the same hash code for equal values")
    void shouldHaveSameHashCodeForEqualValues() {
        // Arrange
        ZipCode one = newZipCode();
        ZipCode two = newZipCode();

        // Act
        // Assert
        assertThat(one.hashCode()).isEqualTo(two.hashCode());
    }

    @Test
    @DisplayName("should have different hash code for different values")
    void shouldHaveDifferentHashCodeForDifferentValues() {
        // Arrange
        ZipCode one = newZipCode();
        ZipCode two = new ZipCode("17065340", LOGRADOURO, BAIRRO, LOCALITY, UF);

        // Act
        // Assert
        assertThat(one.hashCode()).isNotEqualTo(two.hashCode());
    }
}
