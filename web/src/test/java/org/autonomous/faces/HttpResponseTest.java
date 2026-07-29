package org.autonomous.faces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextStub;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link HttpResponse}.
 */
@DisplayName("HttpResponse")
class HttpResponseTest {

    @AfterEach
    void tearDown() {
        FacesContextStub.clear();
    }

    @Test
    @DisplayName("get() returns the HttpServletResponse from the current external context")
    void getReturnsResponseFromExternalContext() {
        // Arrange
        FacesContext context = mock(FacesContext.class);
        ExternalContext external = mock(ExternalContext.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FacesContextStub.setCurrent(context);
        when(context.getExternalContext()).thenReturn(external);
        when(external.getResponse()).thenReturn(response);

        HttpResponse provider = new HttpResponse();

        // Act
        HttpServletResponse result = provider.get();

        // Assert
        assertThat(result).isSameAs(response);
    }
}
