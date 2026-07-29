package org.autonomous.faces.ioc.modules;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Unit tests for {@link WebModule}.
 */
@DisplayName("WebModule")
class WebModuleTest {

    @Test
    @DisplayName("configure() does not throw")
    void configureDoesNotThrow() {
        // Arrange
        WebModule module = new WebModule();

        // Act / Assert
        assertThatCode(() -> {
            Injector injector = Guice.createInjector(module);
            assertThat(injector).isNotNull();
        }).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("configure() binds HttpServletRequest and HttpServletResponse to their providers")
    void configureBindsRequestAndResponseProviders() {
        // Arrange
        WebModule module = new WebModule();

        // Act
        Injector injector = Guice.createInjector(module);

        // Assert
        // The providers delegate to FacesContext.getCurrentInstance(), so a real
        // call would fail outside a JSF request. We only assert that the bindings
        // exist by verifying the injector knows about the provider links.
        assertThat(injector.getBinding(javax.servlet.http.HttpServletRequest.class)).isNotNull();
        assertThat(injector.getBinding(javax.servlet.http.HttpServletResponse.class)).isNotNull();
    }
}
