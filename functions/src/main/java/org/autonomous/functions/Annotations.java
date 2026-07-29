package org.autonomous.functions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Utility class to obtain information from a given annotation.
 *
 * @author arthemus
 * @since 30/08/2013
 */
public class Annotations {

	private final Class<?> classObject;

	private Annotation annotation;
	private Field fieldClass;

	private Annotations(Object object) {
		this.classObject = object.getClass();
	}

	private Annotations(Class<?> classObject) {
		this.classObject = classObject;
	}

	/**
	 * Defines the object whose annotations will be analyzed.
	 *
	 * @param object
	 *            The object to analyze.
	 * @return An Annotations instance.
	 */
	public static Annotations onObject(Object object) {
		return new Annotations(object);
	}

	public static Annotations onClass(Class<?> classReference) {
		return new Annotations(classReference);
	}

	/**
	 * The name of the attribute to be analyzed.
	 *
	 * Ex.
	 *
	 * @Column(name = "NOME_CLIENTE") private String customerName;
	 *
	 *              fieldName = customerName;
	 *
	 * @param fieldName
	 *            The name of the field to find.
	 * @return This Annotations instance for chaining.
	 */
	public Annotations forAttribute(String fieldName) {
		fieldClass = null;
		Field[] fields = classObject.getDeclaredFields();
		for (Field f : fields) {
			if (f.getName() == fieldName) {
				fieldClass = f;
				break;
			} else
				continue;
		}
		if (fieldClass == null)
			throw new RuntimeException("The attribute " + fieldName
					+ " was not found");
		return this;
	}

	/**
	 * Which annotation should be analyzed.
	 *
	 * @param annotationClass
	 *            The annotation class to look for.
	 * @return This Annotations instance for chaining.
	 */
	public Annotations forAnnotation(Class<? extends Annotation> annotationClass) {
		this.annotation = fieldClass.getAnnotation(annotationClass);
		return this;
	}

	/**
	 * Obtains the value of a given attribute of the annotation itself.
	 *
	 * Ex.
	 *
	 * @Column(name = "NOME_CLIENTE", length = 15)
	 *
	 *              paramName = length paramType = Integer.class
	 *
	 * @param paramName
	 *            The name of the annotation parameter.
	 * @param paramType
	 *            The expected type of the parameter.
	 * @return The value of the annotation parameter.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getValue(String paramName, Class<T> paramType) {
		T result = (T) paramType;
		try {
			Method[] methods = this.annotation.annotationType().getMethods();
			for (int count = 0; count < methods.length; count++) {
				Method met = methods[count];
				if (met.getName() == paramName) {
					met.setAccessible(true);
					return (T) met.invoke(annotation);
				}
			}
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("The parameter could not be found: "
					+ e.getMessage());
		} catch (IllegalAccessException e) {
			throw new RuntimeException("The parameter could not be accessed: "
					+ e.getMessage());
		} catch (SecurityException e) {
			throw new RuntimeException(
					"Security problem accessing the parameter: "
							+ e.getMessage());
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Problem accessing the parameter "
					+ paramName + ": " + e.getMessage());
		}
		return result;
	}
}
