package org.autonomous.tenaz;

import static org.junit.Assert.assertEquals;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.autonomous.tenaz.hibernate.MyEntity;
import org.junit.Test;

/**
 *
 * @author arthemus
 * @since 16/05/2013
 */
public class MyEntityTest {

	@Entity
	@Table(name = "TEST_TABLE")
	class TestEntity {

		@Id
		private Integer id;
		@Column
		private String name;

		private TestEntity(Integer id, String name) {
			this.id = id;
			this.name = name;
		}

		public Integer getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	@Test
	public void obtainsAReferenceToTheRealClass() {
		Object object = new TestEntity(2, "Arthemus");
		assertEquals(TestEntity.class, object.getClass());
	}

	@Test
	public void checksTheClassItself() {
		Object object = new TestEntity(25, "Test Value");
		MyEntity entity = new MyEntity(object);
		assertEquals(object.getClass(), entity.getRealClass());
	}

	@Test
	public void checksTheIdFieldName() {
		Object object = new TestEntity(5, "Test Field");
		MyEntity entity = new MyEntity(object);
		assertEquals("id", entity.getIdField());
	}

	@Test
	public void checksTheIdFieldValue() {
		Object object = new TestEntity(25, "Test Value");
		MyEntity entity = new MyEntity(object);
		assertEquals(25, entity.getIdValue());
	}

	@Test
	public void obtainsTheTableName() {
		assertEquals("TEST_TABLE",
				MyEntity.getRelationalTableName(TestEntity.class));
	}
}
