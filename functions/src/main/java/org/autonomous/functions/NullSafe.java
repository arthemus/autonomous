package org.autonomous.functions;

/**
 * This helper class assists when working with external files or unstructured
 * data sources such as XML, JSON, TXT files, or outdated databases with
 * numerous null fields, preventing NullPointerException or
 * ArithmeticException from being thrown and ensuring that at least default
 * values are delivered to the client class.
 * <p>
 * The original name 'Geleia' was a reference to the Ghostbusters mascot, as
 * this class aims to obtain values from objects that may not always 'exist'.
 *
 * @author arthemus
 * @since 07/02/2014
 */
public final class NullSafe {

    /**
     * This method ensures that even if the object passed as an argument is
     * null, a new instance of it will be created so that one of its methods
     * can be used without the risk of getting a NullPointerException.
     * <p>
     * This "trick" should only be used in cases such as handling classes
     * obtained from XML or JSON files.
     *
     * @param reference
     *            The class reference for instantiation.
     * @param object
     *            The object to check.
     * @return The original object or a new instance if null.
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
     * Safely obtains a String value, avoiding NullPointerException.
     *
     * @param value
     *            The value to check.
     * @return The original value or an empty string if null.
     */
    public static String stringNotNull(final String value) {
        if (Functions.isExists(value))
            return String.valueOf(value);
        return new String();
    }

    /**
     * Safely obtains a byte value, avoiding NullPointerException.
     *
     * @param value
     *            The value to parse.
     * @return The parsed byte value or 0 if null or invalid.
     */
    public static Byte byteNotNull(final String value) {
        if (Functions.isExists(value))
            try {
                return Byte.valueOf(value);
            } catch (NumberFormatException e) {
                return 0;
            }
        return 0;
    }

    /**
     * Safely obtains a short value, avoiding NullPointerException.
     *
     * @param value
     *            The value to parse.
     * @return The parsed short value or 0 if null or invalid.
     */
    public static Short shortNotNull(final String value) {
        if (Functions.isExists(value))
            try {
                return Short.valueOf(value);
            } catch (NumberFormatException e) {
                return 0;
            }
        return 0;
    }

    /**
     * Safely obtains an integer value, avoiding NullPointerException.
     *
     * @param value
     *            The value to parse.
     * @return The parsed integer value or 0 if null or invalid.
     */
    public static Integer intNotNull(final String value) {
        if (Functions.isExists(value))
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
     * Safely obtains a long value, avoiding NullPointerException.
     *
     * @param value
     *            The value to parse.
     * @return The parsed long value or 0 if null or invalid.
     */
    public static Long longNotNull(final String value) {
        if (Functions.isExists(value))
            try {
                return Long.valueOf(value);
            } catch (NumberFormatException e) {
                return 0L;
            }
        return 0L;
    }

    /**
     * Safely obtains a float value, avoiding NullPointerException.
     *
     * @param value
     *            The value to parse.
     * @return The parsed float value or 0 if null or invalid.
     */
    public static Float floatNotNull(final String value) {
        if (Functions.isExists(value))
            try {
                return Float.valueOf(value);
            } catch (NumberFormatException e) {
                return 0F;
            }
        return 0F;
    }

    /**
     * Safely obtains a double value, avoiding NullPointerException.
     *
     * @param value
     *            The value to parse.
     * @return The parsed double value or 0 if null or invalid.
     */
    public static Double doubleNotNull(final String value) {
        if (Functions.isExists(value))
            try {
                return Double.valueOf(value);
            } catch (NumberFormatException e) {
                return 0D;
            }
        return 0D;
    }

}
