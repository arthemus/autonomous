package org.autonomous.printer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link WebReport}.
 * <p>
 * The real {@code JasperRunManager.runReportToPdfStream} is a static call that
 * cannot be mocked without the inline mock maker (which is incompatible with
 * {@code FacesContext} on Java 8). Instead the report resource is intentionally
 * absent so {@code prepareStream} returns {@code null}; the Jasper call then
 * throws, which exercises {@code declarePdfFile} (header setup) and the
 * {@code finally} block (flush, close and {@code responseComplete}).
 */
@DisplayName("WebReport")
@ExtendWith(MockitoExtension.class)
class WebReportTest {

    @Mock
    private FacesContext context;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ServletOutputStream outputStream;

    private WebReport report;

    @BeforeEach
    void setUp() throws Exception {
        report = new WebReport(context, response);
    }

    @Test
    @DisplayName("constructor with FacesContext resolves the response from the external context")
    void constructorWithFacesContextResolvesResponse() {
        // Arrange
        ExternalContext external = mock(ExternalContext.class);
        HttpServletResponse resolved = mock(HttpServletResponse.class);
        when(context.getExternalContext()).thenReturn(external);
        when(external.getResponse()).thenReturn(resolved);

        // Act
        WebReport fromContext = new WebReport(context);

        // Assert
        assertThat(fromContext).isNotNull();
        verify(external).getResponse();
    }

    @Test
    @DisplayName("printPdf with parameters sets headers and flushes the stream even when the report is missing")
    void printPdfWithParametersSetsHeadersAndClosesStream() throws Exception {
        // Arrange
        Map<String, Object> parameters = new HashMap<>();
        when(response.getOutputStream()).thenReturn(outputStream);

        // Act
        try {
            report.printPdf(WebReportTest.class, "missing-report.jasper", "my-report", parameters);
        } catch (Exception expected) {
            // expected: the report resource does not exist
        }

        // Assert
        verify(response).setHeader("Content-Disposition", "inline; filename=my-report.pdf");
        verify(response).setContentType("application/pdf");
        verify(outputStream).flush();
        verify(outputStream).close();
        verify(context).responseComplete();
    }

    @Test
    @DisplayName("printPdf with collection data source sets headers and flushes the stream even when the report is missing")
    void printPdfWithCollectionSetsHeadersAndClosesStream() throws Exception {
        // Arrange
        Map<String, Object> parameters = new HashMap<>();
        Collection<String> data = Collections.singleton("row");
        when(response.getOutputStream()).thenReturn(outputStream);

        // Act
        try {
            report.printPdf(WebReportTest.class, "missing-report.jasper", "my-report", parameters, data);
        } catch (Exception expected) {
            // expected: the report resource does not exist
        }

        // Assert
        verify(response).setHeader("Content-Disposition", "inline; filename=my-report.pdf");
        verify(response).setContentType("application/pdf");
        verify(outputStream).flush();
        verify(outputStream).close();
        verify(context).responseComplete();
    }

    @Test
    @DisplayName("printPdf with JDBC connection sets headers and flushes the stream even when the report is missing")
    void printPdfWithConnectionSetsHeadersAndClosesStream() throws Exception {
        // Arrange
        Map<String, Object> parameters = new HashMap<>();
        java.sql.Connection connection = mock(java.sql.Connection.class);
        when(response.getOutputStream()).thenReturn(outputStream);

        // Act
        try {
            report.printPdf(WebReportTest.class, "missing-report.jasper", "my-report", parameters, connection);
        } catch (Exception expected) {
            // expected: the report resource does not exist
        }

        // Assert
        verify(response).setHeader("Content-Disposition", "inline; filename=my-report.pdf");
        verify(response).setContentType("application/pdf");
        verify(outputStream).flush();
        verify(outputStream).close();
        verify(context).responseComplete();
    }
}
