package org.autonomous.functions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Classe utilitária para obter informações de uma determinada Annotation.
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
	 * Define o objeto que terá suas annotations analisadas.
	 * 
	 * @param object
	 * @return
	 */
	public static Annotations onObject(Object object) {
		return new Annotations(object);
	}

	public static Annotations onClass(Class<?> classReference) {
		return new Annotations(classReference);
	}

	/**
	 * O nome do atributo que deve ser analisado.
	 * 
	 * Ex.
	 * 
	 * @Column(name = "NOME_CLIENTE") private String nomeDoCliente;
	 * 
	 *              fieldName = nomeDoCliente;
	 * 
	 * @param fieldName
	 * @return
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
			throw new RuntimeException("O atributo " + fieldName
					+ " não foi encontrado");
		return this;
	}

	/**
	 * Qual annotation deve ser analisada.
	 * 
	 * @param annotationClass
	 * @return
	 */
	public Annotations forAnnotation(Class<? extends Annotation> annotationClass) {
		this.annotation = fieldClass.getAnnotation(annotationClass);
		return this;
	}

	/**
	 * Obtem o valor de um determinado atributo da anotação em sí.
	 * 
	 * Ex.
	 * 
	 * @Column(name = "NOME_CLIENTE", lenght = 15)
	 * 
	 *              paramName = length paramType = Integer.class
	 * 
	 * @param paramName
	 * @param paramType
	 * @return
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
			throw new RuntimeException("O paramêtro não pode ser encontrado: "
					+ e.getMessage());
		} catch (IllegalAccessException e) {
			throw new RuntimeException("O paramêtro não pode ser acessado: "
					+ e.getMessage());
		} catch (SecurityException e) {
			throw new RuntimeException(
					"Problemas de segurança para acessar o paramêtro: "
							+ e.getMessage());
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Problemas para acessar o paramêtro "
					+ paramName + ": " + e.getMessage());
		}
		return result;
	}
}
