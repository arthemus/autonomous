package org.autonomous.tenaz.hibernate;

import java.lang.reflect.Field;

import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class used to obtain information from a possible object-relational entity.
 *
 * @author arthemus
 * @since 16/05/2013
 */
public class MyEntity {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyEntity.class);

	private final Class<?> aClass;
	private final Object aObject;

	public MyEntity(Object object) {
		this.aObject = object;
		this.aClass = object.getClass();
	}

	/**
	 * Returns the name of the field annotated as the primary key.
	 *
	 * @return The id field name, or {@code null} when none is found.
	 */
	public String getIdField() {
		String field = null;
		Field[] fields = aClass.getDeclaredFields();
		for (Field f : fields) {
			Id id = f.getAnnotation(Id.class);
			if (id == null) {
				continue;
			}
			field = f.getName();
		}
		return field;
	}

	/**
	 * Returns the simple name of the entity class.
	 *
	 * @return The class simple name.
	 */
	public String getClassName() {
		return aClass.getSimpleName();
	}

	/**
	 * Returns the real class reference of the wrapped object.
	 *
	 * @return The class reference.
	 */
	public Object getRealClass() {
		return aClass;
	}

	/**
	 * Returns the value of the field annotated as the primary key.
	 *
	 * @return The id value, or {@code null} when none is found.
	 */
	public Object getIdValue() {
		Field[] fields = aClass.getDeclaredFields();
		Object value = null;
		for (Field f : fields) {
			Id id = f.getAnnotation(Id.class);
			if (id == null) {
				continue;
			}
			try {
				f.setAccessible(true);
				value = f.get(aObject);
			} catch (IllegalArgumentException e) {
				LOGGER.error("Illegal argument while reading the id field of {}", aClass, e);
			} catch (IllegalAccessException e) {
				LOGGER.error("Illegal access while reading the id field of {}", aClass, e);
			}
		}
		return value;
	}

	/**
	 * Obtains the name of the table related to the entity, i.e. the value
	 * defined in the {@code @Table} annotation.
	 *
	 * @param classReference
	 *            The entity class reference.
	 * @return The relational table name.
	 */
	public static String getRelationalTableName(Class<?> classReference) {
		Table table = classReference.getAnnotation(Table.class);
		return table.name();
	}

}
