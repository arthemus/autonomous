package org.autonomous.tenaz.commons;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Crud} contract via a minimal implementation.
 */
class CrudTest {

	private static final class TestEntity implements PersistentEntity {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isNewInstance() {
			return true;
		}
	}

	private static final class TestCrud implements Crud<TestEntity> {

		@Override
		public String doCreate(TestEntity object) throws CrudException {
			return "created";
		}

		@Override
		public TestEntity getRead(Class<TestEntity> classReference, Object key) throws CrudException {
			return new TestEntity();
		}

		@Override
		public Collection<TestEntity> getList(Class<TestEntity> classReference) throws CrudException {
			return Arrays.asList(new TestEntity(), new TestEntity());
		}

		@Override
		public String doUpdate(TestEntity object) throws CrudException {
			return "updated";
		}

		@Override
		public String doDelete(TestEntity object) throws CrudException {
			return "deleted";
		}

		@Override
		public String doDelete(Collection<TestEntity> listObject) throws CrudException {
			return "deleted all";
		}

		@Override
		public void doPrint() throws CrudException {
			// no operation
		}

		@Override
		public TestEntity getNewInstance() {
			return new TestEntity();
		}
	}

	@Test
	@DisplayName("should return success message when doCreate is called")
	void shouldReturnSuccessMessageWhenDoCreateIsCalled() throws CrudException {
		// Arrange
		TestCrud crud = new TestCrud();

		// Act
		String result = crud.doCreate(new TestEntity());

		// Assert
		assertThat(result).isEqualTo("created");
	}

	@Test
	@DisplayName("should return entity when getRead is called")
	void shouldReturnEntityWhenGetReadIsCalled() throws CrudException {
		// Arrange
		TestCrud crud = new TestCrud();

		// Act
		TestEntity result = crud.getRead(TestEntity.class, 1);

		// Assert
		assertThat(result).isNotNull();
	}

	@Test
	@DisplayName("should return collection when getList is called")
	void shouldReturnCollectionWhenGetListIsCalled() throws CrudException {
		// Arrange
		TestCrud crud = new TestCrud();

		// Act
		Collection<TestEntity> result = crud.getList(TestEntity.class);

		// Assert
		assertThat(result).hasSize(2);
	}

	@Test
	@DisplayName("should return success message when doUpdate is called")
	void shouldReturnSuccessMessageWhenDoUpdateIsCalled() throws CrudException {
		// Arrange
		TestCrud crud = new TestCrud();

		// Act
		String result = crud.doUpdate(new TestEntity());

		// Assert
		assertThat(result).isEqualTo("updated");
	}

	@Test
	@DisplayName("should return success message when doDelete single is called")
	void shouldReturnSuccessMessageWhenDoDeleteSingleIsCalled() throws CrudException {
		// Arrange
		TestCrud crud = new TestCrud();

		// Act
		String result = crud.doDelete(new TestEntity());

		// Assert
		assertThat(result).isEqualTo("deleted");
	}

	@Test
	@DisplayName("should return success message when doDelete collection is called")
	void shouldReturnSuccessMessageWhenDoDeleteCollectionIsCalled() throws CrudException {
		// Arrange
		TestCrud crud = new TestCrud();

		// Act
		String result = crud.doDelete(Collections.singletonList(new TestEntity()));

		// Assert
		assertThat(result).isEqualTo("deleted all");
	}

	@Test
	@DisplayName("should return new instance when getNewInstance is called")
	void shouldReturnNewInstanceWhenGetNewInstanceIsCalled() {
		// Arrange
		TestCrud crud = new TestCrud();

		// Act
		TestEntity result = crud.getNewInstance();

		// Assert
		assertThat(result).isNotNull();
	}
}
