package org.autonomous.tenaz.hibernate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import javax.faces.application.Application;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link HibernateSessionCloseListener}.
 *
 * <p>
 * The {@code processEvent} path that handles {@link PreDestroyApplicationEvent}
 * delegates to {@link HibernatePersist#getDefault()} which loads configuration
 * from the classpath, so only the non-destructive event path is exercised here.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
class HibernateSessionCloseListenerTest {

	private final HibernateSessionCloseListener listener = new HibernateSessionCloseListener();

	@Mock
	private Application application;

	@Mock
	private SystemEvent otherEvent;

	@Test
	@DisplayName("should return true when source is an Application")
	void shouldReturnTrueWhenSourceIsApplication() {
		// Arrange
		// Act
		boolean result = listener.isListenerForSource(application);

		// Assert
		assertThat(result).isTrue();
	}

	@Test
	@DisplayName("should return false when source is not an Application")
	void shouldReturnFalseWhenSourceIsNotApplication() {
		// Arrange
		Object source = new Object();

		// Act
		boolean result = listener.isListenerForSource(source);

		// Assert
		assertThat(result).isFalse();
	}

	@Test
	@DisplayName("should do nothing when event is not PreDestroyApplicationEvent")
	void shouldDoNothingWhenEventIsNotPreDestroyApplicationEvent() {
		// Arrange
		// Act
		// Assert
		assertThatCode(() -> listener.processEvent(otherEvent)).doesNotThrowAnyException();
	}

	@Test
	@DisplayName("should implement SystemEventListener")
	void shouldImplementSystemEventListener() {
		// Arrange
		// Act
		// Assert
		assertThat(listener).isInstanceOf(javax.faces.event.SystemEventListener.class);
	}
}
