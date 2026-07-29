package org.autonomous.tenaz.commons;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link PersistentEntity} contract.
 */
class PersistentEntityTest {

	/**
	 * Minimal concrete implementation used to exercise the contract.
	 */
	private static final class TestEntity implements PersistentEntity {

		private static final long serialVersionUID = 1L;

		private final boolean newInstance;

		TestEntity(boolean newInstance) {
			this.newInstance = newInstance;
		}

		@Override
		public boolean isNewInstance() {
			return newInstance;
		}
	}

	@Test
	@DisplayName("should return true when entity has not been persisted yet")
	void shouldReturnTrueWhenEntityIsNew() {
		// Arrange
		TestEntity entity = new TestEntity(true);

		// Act
		boolean result = entity.isNewInstance();

		// Assert
		assertThat(result).isTrue();
	}

	@Test
	@DisplayName("should return false when entity is being edited")
	void shouldReturnFalseWhenEntityIsNotNew() {
		// Arrange
		TestEntity entity = new TestEntity(false);

		// Act
		boolean result = entity.isNewInstance();

		// Assert
		assertThat(result).isFalse();
	}

	@Test
	@DisplayName("should be serializable")
	void shouldBeSerializable() {
		// Arrange
		TestEntity entity = new TestEntity(true);

		// Act
		boolean result = entity instanceof java.io.Serializable;

		// Assert
		assertThat(result).isTrue();
	}
}
