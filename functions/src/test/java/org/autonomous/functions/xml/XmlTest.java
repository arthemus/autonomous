package org.autonomous.functions.xml;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

/**
 * Unit tests for the {@link Xml} class.
 */
class XmlTest {

    private static final String XML_CONTENT =
            "<root><name>autonomous</name><version>1.0</version></root>";

    private Xml newXml() throws Exception {
        InputStream stream = new ByteArrayInputStream(
                XML_CONTENT.getBytes(StandardCharsets.UTF_8));
        return new Xml(stream);
    }

    @Test
    @DisplayName("should build document from input stream")
    void shouldBuildDocumentFromInputStream() throws Exception {
        // Arrange
        // Act
        Xml xml = newXml();

        // Assert
        assertThat(xml).isNotNull();
        assertThat(xml.getDocument()).isNotNull();
    }

    @Test
    @DisplayName("should return the document instance")
    void shouldReturnTheDocumentInstance() throws Exception {
        // Arrange
        Xml xml = newXml();

        // Act
        Document document = xml.getDocument();

        // Assert
        assertThat(document).isNotNull();
        assertThat(document.getDocumentElement().getNodeName()).isEqualTo("root");
    }

    @Test
    @DisplayName("should return the value of an existing tag")
    void shouldReturnTheValueOfAnExistingTag() throws Exception {
        // Arrange
        Xml xml = newXml();

        // Act
        String value = xml.getTag("name");

        // Assert
        assertThat(value).isEqualTo("autonomous");
    }

    @Test
    @DisplayName("should return the value of another existing tag")
    void shouldReturnTheValueOfAnotherExistingTag() throws Exception {
        // Arrange
        Xml xml = newXml();

        // Act
        String value = xml.getTag("version");

        // Assert
        assertThat(value).isEqualTo("1.0");
    }

    @Test
    @DisplayName("should return empty string when tag does not exist")
    void shouldReturnEmptyStringWhenTagDoesNotExist() throws Exception {
        // Arrange
        Xml xml = newXml();

        // Act
        String value = xml.getTag("missing");

        // Assert
        assertThat(value).isEmpty();
    }

    @Test
    @DisplayName("should build document from a prebuilt Document object")
    void shouldBuildDocumentFromPrebuiltDocument() throws Exception {
        // Arrange
        InputStream stream = new ByteArrayInputStream(
                XML_CONTENT.getBytes(StandardCharsets.UTF_8));
        javax.xml.parsers.DocumentBuilder builder =
                javax.xml.parsers.DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder();
        Document document = builder.parse(stream);

        // Act
        Xml xml = new Xml(document);

        // Assert
        assertThat(xml.getDocument()).isSameAs(document);
        assertThat(xml.getTag("name")).isEqualTo("autonomous");
    }
}
