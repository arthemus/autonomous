package org.autonomous.faces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextStub;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link HttpRequest}.
 */
@DisplayName("HttpRequest")
class HttpRequestTest {

    @AfterEach
    void tearDown() {
        FacesContextStub.clear();
    }

    @Test
    @DisplayName("get() returns the HttpServletRequest from the current external context")
    void getReturnsRequestFromExternalContext() {
        // Arrange
        FacesContext context = mock(FacesContext.class);
        ExternalContext external = mock(ExternalContext.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        FacesContextStub.setCurrent(context);
        when(context.getExternalContext()).thenReturn(external);
        when(external.getRequest()).thenReturn(request);

        HttpRequest provider = new HttpRequest();

        // Act
        HttpServletRequest result = provider.get();

        // Assert
        assertThat(result).isSameAs(request);
    }
}
