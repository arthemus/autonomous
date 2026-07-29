package org.autonomous.tenaz.hibernate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link OpenSessionInView}.
 *
 * <p>
 * The {@code init()} and {@code doFilter()} methods delegate to
 * {@link HibernatePersist#getDefault()} which loads configuration from the
 * classpath, so only the no-op {@code destroy()} path (when persist is null)
 * is exercised here.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
class OpenSessionInViewTest {

	@Mock
	private ServletRequest request;

	@Mock
	private ServletResponse response;

	@Mock
	private FilterChain chain;

	@Test
	@DisplayName("should implement Filter")
	void shouldImplementFilter() {
		// Arrange
		// Act
		OpenSessionInView filter = new OpenSessionInView();

		// Assert
		assertThat(filter).isInstanceOf(Filter.class);
	}

	@Test
	@DisplayName("should not throw when destroy is called before init")
	void shouldNotThrowWhenDestroyIsCalledBeforeInit() {
		// Arrange
		OpenSessionInView filter = new OpenSessionInView();

		// Act
		// Assert
		assertThatCode(() -> filter.destroy()).doesNotThrowAnyException();
	}
}
