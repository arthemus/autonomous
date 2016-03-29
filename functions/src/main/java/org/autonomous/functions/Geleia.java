package br.com.autonomous.functions;

/**
 * Essa classe auxiliar ajuda quando temos que trabalhar com arquivos externos
 * ou fontes de dados não estruturadas como arquivos XML, JSON, TXT ou base de
 * dados desatualizadas com inumeros campos nulos, evitando que exceções do tipo
 * NullPointerException ou ArithmeticException sejão disparadas e garantindo que
 * ao menos valores 'defaults' sejão entregues a classe cliente.
 * <p>
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
     * <p>
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
    public static Byte byteNotNull(final String value) {
        if (Funcoes.isExists(value))
            try {
                return Byte.valueOf(value);
            } catch (NumberFormatException e) {
                return 0;
            }
        return 0;
    }

    /**
     * Para obter um valor short de forma mais segura evitando
     * NullPointerException.
     *
     * @param value
     * @return
     */
    public static Short shortNotNull(final String value) {
        if (Funcoes.isExists(value))
            try {
                return Short.valueOf(value);
            } catch (NumberFormatException e) {
                return 0;
            }
        return 0;
    }

    /**
     * Para obter um valor integer de forma mais segura evitando
     * NullPointerException.
     *
     * @param value
     * @return
     */
    public static Integer intNotNull(final String value) {
        if (Funcoes.isExists(value))
            try {
                return Integer.valueOf(value);
            } catch (NumberFormatException e) {
                return 0;
            }
        return 0;
    }

    public static Integer intNotNull(final Integer value) {
        if (value == null)
            return 0;
        return Integer.valueOf(value);
    }

    /**
     * Para obter um valor long de forma mais segura evitando
     * NullPointerException.
     *
     * @param value
     * @return
     */
    public static Long longNotNull(final String value) {
        if (Funcoes.isExists(value))
            try {
                return Long.valueOf(value);
            } catch (NumberFormatException e) {
                return 0L;
            }
        return 0L;
    }

    /**
     * Para obter um valor float de forma mais segura evitando
     * NullPointerException.
     *
     * @param value
     * @return
     */
    public static Float floatNotNull(final String value) {
        if (Funcoes.isExists(value))
            try {
                return Float.valueOf(value);
            } catch (NumberFormatException e) {
                return 0F;
            }
        return 0F;
    }

    /**
     * Para obter um valor double de forma mais segura evitando
     * NullPointerException.
     *
     * @param value
     * @return
     */
    public static Double doubleNotNull(final String value) {
        if (Funcoes.isExists(value))
            try {
                return Double.valueOf(value);
            } catch (NumberFormatException e) {
                return 0D;
            }
        return 0D;
    }

}