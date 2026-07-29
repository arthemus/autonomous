package org.autonomous.tenaz.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.autonomous.functions.messaging.Messages;
import org.autonomous.tenaz.commons.CrudException;
import org.autonomous.tenaz.commons.PersistentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for {@link AbstractCrud}.
 */
@ExtendWith(MockitoExtension.class)
class AbstractCrudTest {

	private static final class TestEntity implements PersistentEntity {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isNewInstance() {
			return true;
		}
	}

	private static final class TestCrud extends AbstractCrud<TestEntity> {

		TestCrud(Persist persist, Messages i18n) {
			super(persist, i18n);
		}

		@Override
		public TestEntity getNewInstance() {
			return new TestEntity();
		}
	}

	@Mock
	private Persist persist;

	@Mock
	private Messages messages;

	private TestCrud crud;

	@BeforeEach
	void setUp() {
		crud = new TestCrud(persist, messages);
	}

	@Test
	@DisplayName("should return success message and save entity when doCreate succeeds")
	void shouldReturnSuccessMessageAndSaveEntityWhenDoCreateSucceeds() throws Exception {
		// Arrange
		TestEntity entity = new TestEntity();
		when(messages.getMessage("crud.docreate.success")).thenReturn("created");
		when(persist.save(any(Serializable.class))).thenReturn(persist);

		// Act
		String result = crud.doCreate(entity);

		// Assert
		assertThat(result).isEqualTo("created");
		verify(persist).save(entity);
	}

	@Test
	@DisplayName("should throw CrudException when doCreate fails")
	void shouldThrowCrudExceptionWhenDoCreateFails() throws Exception {
		// Arrange
		TestEntity entity = new TestEntity();
		when(messages.getMessage("crud.docreate.unsuccess")).thenReturn("create failed: ");
		doThrow(new PersistException("boom")).when(persist).save(any(Serializable.class));

		// Act
		// Assert
		assertThatThrownBy(() -> crud.doCreate(entity))
				.isInstanceOf(CrudException.class)
				.hasMessageContaining("create failed: ")
				.hasMessageContaining("boom");
	}

	@Test
	@DisplayName("should return entity when getRead succeeds")
	void shouldReturnEntityWhenGetReadSucceeds() throws Exception {
		// Arrange
		TestEntity entity = new TestEntity();
		when(persist.get(TestEntity.class, 1)).thenReturn(entity);

		// Act
		TestEntity result = crud.getRead(TestEntity.class, 1);

		// Assert
		assertThat(result).isSameAs(entity);
	}

	@Test
	@DisplayName("should throw CrudException when getRead fails")
	void shouldThrowCrudExceptionWhenGetReadFails() throws Exception {
		// Arrange
		when(messages.getMessage("crud.search.unsuccess")).thenReturn("search failed: ");
		when(persist.get(TestEntity.class, 1)).thenThrow(new PersistException("not found"));

		// Act
		// Assert
		assertThatThrownBy(() -> crud.getRead(TestEntity.class, 1))
				.isInstanceOf(CrudException.class)
				.hasMessageContaining("search failed: ")
				.hasMessageContaining("not found");
	}

	@Test
	@DisplayName("should return list when getList succeeds")
	void shouldReturnListWhenGetListSucceeds() throws Exception {
		// Arrange
		List<TestEntity> entities = Arrays.asList(new TestEntity(), new TestEntity());
		when(persist.list(TestEntity.class)).thenReturn(entities);

		// Act
		Collection<TestEntity> result = crud.getList(TestEntity.class);

		// Assert
		assertThat(result).hasSize(2);
	}

	@Test
	@DisplayName("should throw CrudException when getList fails")
	void shouldThrowCrudExceptionWhenGetListFails() throws Exception {
		// Arrange
		when(messages.getMessage("crud.search.unsuccess")).thenReturn("search failed: ");
		when(persist.list(TestEntity.class)).thenThrow(new PersistException("db error"));

		// Act
		// Assert
		assertThatThrownBy(() -> crud.getList(TestEntity.class))
				.isInstanceOf(CrudException.class)
				.hasMessageContaining("search failed: ")
				.hasMessageContaining("db error");
	}

	@Test
	@DisplayName("should return success message and update entity when doUpdate succeeds")
	void shouldReturnSuccessMessageAndUpdateEntityWhenDoUpdateSucceeds() throws Exception {
		// Arrange
		TestEntity entity = new TestEntity();
		when(messages.getMessage("crud.doupdate.success")).thenReturn("updated");
		when(persist.update(any(Serializable.class))).thenReturn(persist);

		// Act
		String result = crud.doUpdate(entity);

		// Assert
		assertThat(result).isEqualTo("updated");
		verify(persist).update(entity);
	}

	@Test
	@DisplayName("should throw CrudException when doUpdate fails")
	void shouldThrowCrudExceptionWhenDoUpdateFails() throws Exception {
		// Arrange
		TestEntity entity = new TestEntity();
		when(messages.getMessage("crud.doupdate.unsuccess")).thenReturn("update failed: ");
		doThrow(new PersistException("boom")).when(persist).update(any(Serializable.class));

		// Act
		// Assert
		assertThatThrownBy(() -> crud.doUpdate(entity))
				.isInstanceOf(CrudException.class)
				.hasMessageContaining("update failed: ")
				.hasMessageContaining("boom");
	}

	@Test
	@DisplayName("should return success message and delete entity when doDelete single succeeds")
	void shouldReturnSuccessMessageAndDeleteEntityWhenDoDeleteSingleSucceeds() throws Exception {
		// Arrange
		TestEntity entity = new TestEntity();
		when(messages.getMessage("crud.dodelete.success")).thenReturn("deleted");
		when(persist.delete(any(Serializable.class))).thenReturn(persist);

		// Act
		String result = crud.doDelete(entity);

		// Assert
		assertThat(result).isEqualTo("deleted");
		verify(persist).delete(entity);
	}

	@Test
	@DisplayName("should throw CrudException when doDelete single fails")
	void shouldThrowCrudExceptionWhenDoDeleteSingleFails() throws Exception {
		// Arrange
		TestEntity entity = new TestEntity();
		when(messages.getMessage("crud.dodelete.unsuccess")).thenReturn("delete failed: ");
		doThrow(new PersistException("boom")).when(persist).delete(any(Serializable.class));

		// Act
		// Assert
		assertThatThrownBy(() -> crud.doDelete(entity))
				.isInstanceOf(CrudException.class)
				.hasMessageContaining("delete failed: ")
				.hasMessageContaining("boom");
	}

	@Test
	@DisplayName("should return success message when doDelete collection succeeds")
	void shouldReturnSuccessMessageWhenDoDeleteCollectionSucceeds() throws Exception {
		// Arrange
		List<TestEntity> entities = Collections.singletonList(new TestEntity());
		when(messages.getMessage("crud.dodeleteplural.success")).thenReturn("deleted all");
		when(persist.delete(any(Collection.class))).thenReturn(persist);

		// Act
		String result = crud.doDelete(entities);

		// Assert
		assertThat(result).isEqualTo("deleted all");
		verify(persist).delete(entities);
	}

	@Test
	@DisplayName("should throw CrudException when doDelete collection fails")
	void shouldThrowCrudExceptionWhenDoDeleteCollectionFails() throws Exception {
		// Arrange
		List<TestEntity> entities = Collections.singletonList(new TestEntity());
		when(messages.getMessage("crud.dodeleteplural.unsuccess")).thenReturn("delete plural failed: ");
		doThrow(new PersistException("boom")).when(persist).delete(any(Collection.class));

		// Act
		// Assert
		assertThatThrownBy(() -> crud.doDelete(entities))
				.isInstanceOf(CrudException.class)
				.hasMessageContaining("delete plural failed: ")
				.hasMessageContaining("boom");
	}

	@Test
	@DisplayName("should do nothing when doPrint is called with default implementation")
	void shouldDoNothingWhenDoPrintIsCalledWithDefaultImplementation() throws Exception {
		// Arrange
		// Act
		// Assert
		crud.doPrint();
	}
}
