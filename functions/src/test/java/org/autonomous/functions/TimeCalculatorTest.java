package org.autonomous.functions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link TimeCalculator} class.
 */
class TimeCalculatorTest {

    private static Time timeOf(String hhmmss) {
        Date baseDate = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        return Time.getInstance(baseDate, hhmmss);
    }

    @Test
    @DisplayName("should return zero time when nothing is added")
    void shouldReturnZeroTimeWhenNothingIsAdded() {
        // Arrange
        TimeCalculator calculator = new TimeCalculator();

        // Act
        String result = calculator.getResult();

        // Assert
        assertThat(result).isEqualTo("00:00:00");
    }

    @Test
    @DisplayName("should return the added time")
    void shouldReturnTheAddedTime() {
        // Arrange
        TimeCalculator calculator = new TimeCalculator();

        // Act
        String result = calculator.add(timeOf("02:30:00")).getResult();

        // Assert
        assertThat(result).isEqualTo("02:30:00");
    }

    @Test
    @DisplayName("should return the sum of multiple added times")
    void shouldReturnTheSumOfMultipleAddedTimes() {
        // Arrange
        TimeCalculator calculator = new TimeCalculator();

        // Act
        String result = calculator
                .add(timeOf("02:30:00"))
                .add(timeOf("01:15:00"))
                .getResult();

        // Assert
        assertThat(result).isEqualTo("03:45:00");
    }

    @Test
    @DisplayName("should return the difference when subtracting a time")
    void shouldReturnTheDifferenceWhenSubtractingATime() {
        // Arrange
        TimeCalculator calculator = new TimeCalculator();

        // Act
        String result = calculator
                .add(timeOf("02:30:00"))
                .subtract(timeOf("01:00:00"))
                .getResult();

        // Assert
        assertThat(result).isEqualTo("01:30:00");
    }

    @Test
    @DisplayName("should be chainable from add and subtract")
    void shouldBeChainableFromAddAndSubtract() {
        // Arrange
        TimeCalculator calculator = new TimeCalculator();

        // Act
        TimeCalculator returned = calculator.add(timeOf("01:00:00"));

        // Assert
        assertThat(returned).isSameAs(calculator);
    }
}
