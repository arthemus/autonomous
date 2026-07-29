package org.autonomous.functions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link TimeUtils} class.
 */
class TimeUtilsTest {

    private static Time timeOf(String hhmmss) {
        Date baseDate = new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime();
        return Time.getInstance(baseDate, hhmmss);
    }

    @Test
    @DisplayName("should return the difference between two times")
    void shouldReturnTheDifferenceBetweenTwoTimes() {
        // Arrange
        Time start = timeOf("10:30:00");
        Time end = timeOf("18:00:00");

        // Act
        String difference = TimeUtils.between(start, end);

        // Assert
        assertThat(difference).isEqualTo("07:30:00");
    }

    @Test
    @DisplayName("should return zero difference when times are equal")
    void shouldReturnZeroDifferenceWhenTimesAreEqual() {
        // Arrange
        Time start = timeOf("12:00:00");
        Time end = timeOf("12:00:00");

        // Act
        String difference = TimeUtils.between(start, end);

        // Assert
        assertThat(difference).isEqualTo("00:00:00");
    }

    @Test
    @DisplayName("should return a new TimeCalculator from calculate")
    void shouldReturnANewTimeCalculatorFromCalculate() {
        // Arrange
        // Act
        TimeCalculator calculator = TimeUtils.calculate();

        // Assert
        assertThat(calculator).isNotNull();
        assertThat(calculator.getResult()).isEqualTo("00:00:00");
    }
}
