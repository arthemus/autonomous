package org.autonomous.functions;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Week} enum.
 */
class WeekTest {

    @Test
    @DisplayName("should return seven enum values")
    void shouldReturnSevenEnumValues() {
        // Arrange
        // Act
        Week[] values = Week.values();

        // Assert
        assertThat(values).hasSize(7);
    }

    @Test
    @DisplayName("should return Sunday when index is one")
    void shouldReturnSundayWhenIndexIsOne() {
        // Arrange
        // Act
        Week day = Week.getDay(1);

        // Assert
        assertThat(day).isEqualTo(Week.SUNDAY);
    }

    @Test
    @DisplayName("should return Monday when index is two")
    void shouldReturnMondayWhenIndexIsTwo() {
        // Arrange
        // Act
        Week day = Week.getDay(2);

        // Assert
        assertThat(day).isEqualTo(Week.MONDAY);
    }

    @Test
    @DisplayName("should return Saturday when index is seven")
    void shouldReturnSaturdayWhenIndexIsSeven() {
        // Arrange
        // Act
        Week day = Week.getDay(7);

        // Assert
        assertThat(day).isEqualTo(Week.SATURDAY);
    }

    @Test
    @DisplayName("should return Monday when index is out of range")
    void shouldReturnMondayWhenIndexIsOutOfRange() {
        // Arrange
        // Act
        Week day = Week.getDay(99);

        // Assert
        assertThat(day).isEqualTo(Week.MONDAY);
    }

    @Test
    @DisplayName("should return Sunday when string index is one")
    void shouldReturnSundayWhenStringIndexIsOne() {
        // Arrange
        // Act
        Week day = Week.getDay("1");

        // Assert
        assertThat(day).isEqualTo(Week.SUNDAY);
    }

    @Test
    @DisplayName("should return correct day when given a known date")
    void shouldReturnCorrectDayWhenGivenKnownDate() {
        // Arrange
        // January 5th, 2020 was a Sunday.
        Date date = new GregorianCalendar(2020, Calendar.JANUARY, 5).getTime();

        // Act
        Week day = Week.getDay(date);

        // Assert
        assertThat(day).isEqualTo(Week.SUNDAY);
    }

    @Test
    @DisplayName("should return a non-null day for today")
    void shouldReturnNonNullDayForToday() {
        // Arrange
        // Act
        Week today = Week.today();

        // Assert
        assertThat(today).isNotNull();
        assertThat(Week.values()).contains(today);
    }

    @Test
    @DisplayName("should return English description for each day")
    void shouldReturnEnglishDescriptionForEachDay() {
        // Arrange
        // Act
        // Assert
        assertThat(Week.SUNDAY.getDescription()).isEqualTo("Sunday");
        assertThat(Week.MONDAY.getDescription()).isEqualTo("Monday");
        assertThat(Week.TUESDAY.getDescription()).isEqualTo("Tuesday");
        assertThat(Week.WEDNESDAY.getDescription()).isEqualTo("Wednesday");
        assertThat(Week.THURSDAY.getDescription()).isEqualTo("Thursday");
        assertThat(Week.FRIDAY.getDescription()).isEqualTo("Friday");
        assertThat(Week.SATURDAY.getDescription()).isEqualTo("Saturday");
    }

    @Test
    @DisplayName("should return correct index for each day")
    void shouldReturnCorrectIndexForEachDay() {
        // Arrange
        // Act
        // Assert
        assertThat(Week.SUNDAY.getIndex()).isEqualTo(1);
        assertThat(Week.MONDAY.getIndex()).isEqualTo(2);
        assertThat(Week.TUESDAY.getIndex()).isEqualTo(3);
        assertThat(Week.WEDNESDAY.getIndex()).isEqualTo(4);
        assertThat(Week.THURSDAY.getIndex()).isEqualTo(5);
        assertThat(Week.FRIDAY.getIndex()).isEqualTo(6);
        assertThat(Week.SATURDAY.getIndex()).isEqualTo(7);
    }
}
