package org.autonomous.ioc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Stage;

/**
 * Unit tests for {@link GuiceAdapter}.
 */
@DisplayName("GuiceAdapter")
class GuiceAdapterTest {

    /** A simple collaborator bound by the test module. */
    static final class Greeting {
        public String hello() {
            return "hello";
        }
    }

    /** Guice module that binds {@link Greeting} to a concrete instance. */
    static final class GreetingModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(Greeting.class).toInstance(new Greeting());
        }
    }

    @Test
    @DisplayName("get(Class, Module...) returns an instance from the injector")
    void getWithVarArgsModulesReturnsInstance() {
        // Arrange
        // Act
        Greeting result = GuiceAdapter.get(Greeting.class, new GreetingModule());

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.hello()).isEqualTo("hello");
    }

    @Test
    @DisplayName("get(Class, Iterable) returns an instance from the injector")
    void getWithIterableModulesReturnsInstance() {
        // Arrange
        List<GreetingModule> modules = Arrays.asList(new GreetingModule());

        // Act
        Greeting result = GuiceAdapter.get(Greeting.class, modules);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.hello()).isEqualTo("hello");
    }

    @Test
    @DisplayName("get(Class, Stage, Module...) honors the supplied stage")
    void getWithStageAndVarArgsReturnsInstance() {
        // Arrange
        // Act
        Greeting result = GuiceAdapter.get(Greeting.class, Stage.DEVELOPMENT, new GreetingModule());

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.hello()).isEqualTo("hello");
    }

    @Test
    @DisplayName("get(Class, Stage, Iterable) honors the supplied stage")
    void getWithStageAndIterableReturnsInstance() {
        // Arrange
        List<GreetingModule> modules = Arrays.asList(new GreetingModule());

        // Act
        Greeting result = GuiceAdapter.get(Greeting.class, Stage.DEVELOPMENT, modules);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.hello()).isEqualTo("hello");
    }
}
