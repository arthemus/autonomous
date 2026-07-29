package org.autonomous.faces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextStub;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Faces}.
 */
@DisplayName("Faces")
class FacesTest {

    @BeforeEach
    void setUp() throws Exception {
        resetFacesContextPathCache();
    }

    @AfterEach
    void tearDown() {
        FacesContextStub.clear();
    }

    /** Resets the cached {@code facesContextPath} so tests do not interfere. */
    private void resetFacesContextPathCache() throws Exception {
        Field field = Faces.class.getDeclaredField("facesContextPath");
        field.setAccessible(true);
        field.set(null, null);
    }

    @Test
    @DisplayName("getName(Part) extracts the file name from the content-disposition header")
    void getNameExtractsFileName() {
        // Arrange
        Part part = mock(Part.class);
        when(part.getHeader("content-disposition"))
                .thenReturn("form-data; name=\"file\"; filename=\"report.pdf\"");

        // Act
        String name = Faces.getName(part);

        // Assert
        assertThat(name).isEqualTo("report.pdf");
    }

    @Test
    @DisplayName("getName(Part) strips a Windows-style leading path from the file name")
    void getNameStripsWindowsLeadingPath() {
        // Arrange
        Part part = mock(Part.class);
        when(part.getHeader("content-disposition"))
                .thenReturn("form-data; name=\"file\"; filename=\"C:\\temp\\files\\report.pdf\"");

        // Act
        String name = Faces.getName(part);

        // Assert
        assertThat(name).isEqualTo("report.pdf");
    }

    @Test
    @DisplayName("getName(Part) strips a unix-style leading path from the file name")
    void getNameStripsUnixLeadingPath() {
        // Arrange
        Part part = mock(Part.class);
        when(part.getHeader("content-disposition"))
                .thenReturn("form-data; name=\"file\"; filename=\"/home/user/report.pdf\"");

        // Act
        String name = Faces.getName(part);

        // Assert
        assertThat(name).isEqualTo("report.pdf");
    }

    @Test
    @DisplayName("getName(Part) returns a fallback message when no filename is present")
    void getNameReturnsFallbackWhenFilenameAbsent() {
        // Arrange
        Part part = mock(Part.class);
        when(part.getHeader("content-disposition"))
                .thenReturn("form-data; name=\"file\"");

        // Act
        String name = Faces.getName(part);

        // Assert
        assertThat(name).isEqualTo("[Could not obtain the file name]");
    }

    @Test
    @DisplayName("getContent(Part) reads the full content of a text part")
    void getContentReadsTextContent() throws IOException {
        // Arrange
        Part part = mock(Part.class);
        when(part.getInputStream()).thenReturn(new ByteArrayInputStream("hello world".getBytes()));

        // Act
        String content = Faces.getContent(part);

        // Assert
        assertThat(content).isEqualTo("hello world");
    }

    @Test
    @DisplayName("getContent(Part) wraps read failures in an IOException with a message")
    void getContentWrapsReadFailure() {
        // Arrange
        Part part = mock(Part.class);
        try {
            when(part.getInputStream()).thenThrow(new IOException("disk error"));
        } catch (IOException e) {
            // ignored - stubbing setup
        }

        // Act / Assert
        assertThatThrownBy(() -> Faces.getContent(part))
                .isInstanceOf(IOException.class)
                .hasMessageContaining("Could not read the file");
    }

    @Test
    @DisplayName("getClientIP() returns the trimmed remote address from the current request")
    void getClientIPReturnsRemoteAddr() {
        // Arrange
        FacesContext context = mock(FacesContext.class);
        ExternalContext external = mock(ExternalContext.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        FacesContextStub.setCurrent(context);
        when(context.getExternalContext()).thenReturn(external);
        when(external.getRequest()).thenReturn(request);
        when(request.getRemoteAddr()).thenReturn("  10.0.0.1  ");

        // Act
        String ip = Faces.getClientIP();

        // Assert
        assertThat(ip).isEqualTo("10.0.0.1");
    }

    @Test
    @DisplayName("getFacesContextPath() resolves and caches the real path from the servlet context")
    void getFacesContextPathResolvesRealPath() {
        // Arrange
        FacesContext context = mock(FacesContext.class);
        ExternalContext external = mock(ExternalContext.class);
        ServletContext servletContext = mock(ServletContext.class);
        FacesContextStub.setCurrent(context);
        when(context.getExternalContext()).thenReturn(external);
        when(external.getContext()).thenReturn(servletContext);
        when(servletContext.getRealPath("/")).thenReturn("/app/root/");

        // Act
        String path = Faces.getFacesContextPath();

        // Assert
        assertThat(path).isEqualTo("/app/root/");
    }

    @Test
    @DisplayName("goTo(String) builds a path under WEB-INF relative to the faces context path")
    void goToBuildsWebInfPath() {
        // Arrange
        FacesContext context = mock(FacesContext.class);
        ExternalContext external = mock(ExternalContext.class);
        ServletContext servletContext = mock(ServletContext.class);
        FacesContextStub.setCurrent(context);
        when(context.getExternalContext()).thenReturn(external);
        when(external.getContext()).thenReturn(servletContext);
        when(servletContext.getRealPath("/")).thenReturn("/app/root");

        // Act
        String target = Faces.goTo("classes/config.xml");

        // Assert
        assertThat(target).isEqualTo("/app/rootWEB-INF/classes/config.xml");
    }
}
