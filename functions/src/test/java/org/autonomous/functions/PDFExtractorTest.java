package org.autonomous.functions;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link PDFExtractor} class.
 *
 * <p>The happy-path text extraction requires a valid binary PDF fixture, so
 * these tests focus on the error-handling paths which return {@code null} or
 * {@code 0} when the input cannot be parsed as a PDF document.</p>
 */
class PDFExtractorTest {

    private InputStream invalidPdf() {
        return new ByteArrayInputStream("not a pdf".getBytes());
    }

    @Test
    @DisplayName("should return null when text cannot be extracted from invalid input")
    void shouldReturnNullWhenTextCannotBeExtractedFromInvalidInput() {
        // Arrange
        PDFExtractor extractor = new PDFExtractor();

        // Act
        String text = extractor.getText(invalidPdf());

        // Assert
        assertThat(text).isNull();
    }

    @Test
    @DisplayName("should return zero pages when input is not a valid PDF")
    void shouldReturnZeroPagesWhenInputIsNotAValidPdf() {
        // Arrange
        PDFExtractor extractor = new PDFExtractor();

        // Act
        int pages = extractor.getTotalPages(invalidPdf());

        // Assert
        assertThat(pages).isZero();
    }

    @Test
    @DisplayName("should return null when extracting a page range from invalid input")
    void shouldReturnNullWhenExtractingPageRangeFromInvalidInput() {
        // Arrange
        PDFExtractor extractor = new PDFExtractor();

        // Act
        String text = extractor.getText(invalidPdf(), 1, 1);

        // Assert
        assertThat(text).isNull();
    }
}
