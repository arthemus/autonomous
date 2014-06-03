package org.autonomous.tenaz.hibernate;

import java.lang.reflect.Field;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe utilizada para obter informações de uma possível entidade objeto
 * relacional.
 * 
 * @author arthemus
 * @since 16/05/2013
 */
public class MyEntity {

	private final Class<?> aClass;
	private final Object aObject;

	public MyEntity(Object object) {
		this.aObject = object;
		this.aClass = object.getClass();
	}

	public String getCampoId() {
		String campo = null;
		Field[] fields = aClass.getDeclaredFields();
		for (Field f : fields) {
			Id id = f.getAnnotation(Id.class);
			if (id == null)
				continue;
			campo = f.getName();
		}
		return campo;
	}

	public String getNomeClasse() {
		return aClass.getSimpleName();
	}

	public Object getRealClass() {
		return aClass;
	}

	public Object getValueId() {
		Field[] fields = aClass.getDeclaredFields();
		Object value = null;
		for (Field f : fields) {
			Id id = f.getAnnotation(Id.class);
			if (id == null)
				continue;
			try {
				f.setAccessible(true);
				value = f.get(aObject);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	/**
	 * Obtem o nome da tabela relacionada a entidade. O valor definido na
	 * Annotation @Table.
	 * 
	 * @return
	 */
	public static String getNomeTabelaRelacional(Class<?> classReference) {
		Table table = classReference.getAnnotation(Table.class);
		return table.name();
	}

}
