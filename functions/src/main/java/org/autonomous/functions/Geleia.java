package org.autonomous.functions;

/**
 * 
 * Essa classe auxiliar ajuda quando temos que trabalhar com arquivos externos
 * ou fontes de dados não estruturadas como arquivos XML, JSON, TXT ou base de
 * dados desatualizadas com inumeros campos nulos, evitando que exceções do tipo
 * NullPointerException ou ArithmeticException sejão disparadas e garantindo que
 * ao menos valores 'defaults' sejão entregues a classe cliente.
 * 
 * O nome 'Geleia' é uma referência ao mascote dos Caça-Fantasmas já que essa classe
 * visa obter valores de objetos que nem sempre 'existirão'.
 * 
 * @author arthemus
 * @since 07/02/2014
 */
public final class Geleia {

	/**
	 * Esse método garante que mesmo que o objeto passado como argumento seja 
	 * nulo, será criada uma nova instancia dele para assim utilizar algum 
	 * de seus método sem o perigo de obter um NullPoiterException.
	 * 
	 * Essa "apelação" só deve ser utilizada em casos como o tratamento de classes
	 * obtidas atravez de arquivos xml ou json. 
	 * 
	 * @param reference
	 * @param object
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws Exception
	 */
	public static <T> T by(Class<T> reference, T object) 
			throws InstantiationException, IllegalAccessException {
		T instance = object;
		if (object == null)
			instance = reference.newInstance();
		return instance;
	}
	
	/**
	 * Para obter um valor String de forma mais segura evitando
	 * NullPointerException.
	 * 
	 * @param value
	 * @return
	 */
	public static String stringNotNull(final String value) {
		if (Funcoes.isExists(value))
			return String.valueOf(value);
		return new String();
	}

	/**
	 * Para obter um valor byte de forma mais segura evitando
	 * NullPointerException.
	 * 
	 * @param value
	 * @return
	 */
	public static byte byteNotNull(final String value) {
		if (Funcoes.isExists(value))
			return Byte.valueOf(value);
		return 0;
	}

	/**
	 * Para obter um valor short de forma mais segura evitando
	 * NullPointerException.
	 * 
	 * @param value
	 * @return
	 */
	public static short shortNotNull(final String value) {
		if (Funcoes.isExists(value))
			return Short.valueOf(value);
		return 0;
	}

	/**
	 * Para obter um valor integer de forma mais segura evitando
	 * NullPointerException.
	 * 
	 * @param value
	 * @return
	 */
	public static int intNotNull(final String value) {
		if (Funcoes.isExists(value))
			return Integer.valueOf(value);
		return 0;
	}

	/**
	 * Para obter um valor long de forma mais segura evitando
	 * NullPointerException.
	 * 
	 * @param value
	 * @return
	 */
	public static long longNotNull(final String value) {
		if (Funcoes.isExists(value))
			return Long.valueOf(value);
		return 0L;
	}

	/**
	 * Para obter um valor float de forma mais segura evitando
	 * NullPointerException.
	 * 
	 * @param value
	 * @return
	 */
	public static float floatNotNull(final String value) {
		if (Funcoes.isExists(value))
			return Float.valueOf(value);
		return 0F;
	}

	/**
	 * Para obter um valor double de forma mais segura evitando
	 * NullPointerException.
	 * 
	 * @param value
	 * @return
	 */
	public static double doubleNotNull(final String value) {
		if (Funcoes.isExists(value))
			return Double.valueOf(value);
		return 0D;
	}

}
