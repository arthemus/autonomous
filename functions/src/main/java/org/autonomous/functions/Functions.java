package org.autonomous.functions;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * General-purpose class with reusable functions for any system.
 * <p>
 * <b>Avoid writing methods that have dependencies beyond the API.</b>
 *
 * @author Arthemus C. Moreira
 * @since 12/09/2011
 */
public class Functions {

    private static final Logger LOGGER = LoggerFactory.getLogger(Functions.class);

    /**
     * Common Portuguese language prepositions. Used for text processing
     * of Portuguese phrases (e.g., removing prepositions from names).
     */
    static final String[] PREPOSITIONS = {"a", "ao", "à", "aos", "às", "de", "do", "da", "dos", "das", "em", "no", "na", "nos", "nas", "por", "pelo", "pela", "pelos", "pelas", "ante", "após", "até", "com", "contra", "desde", "entre", "para", "perante", "sem", "sob", "sobre", "trás"};

    /**
     * Method for generating a log string.
     * Example: John - 12/09/2011 - 22:31:42
     *
     * @param userName
     *            The name of the user.
     * @return A string that can be used as a log for any change in the system.
     * @author Arthemus C. Moreira
     */
    public static String getLogEntry(String userName) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
        return userName.concat(" - ").concat(format.format(new Date()));
    }

    /**
     * Method to obtain a given date for use in a SQL query.
     *
     * @param date
     *            The desired date instantiated from the Date class.
     * @param format
     *            The date format pattern.
     * @return The date as a java.sql.Date for use in SQL queries.
     * @throws ParseException
     * @author Arthemus C. Moreira
     */
    public static Date getSqlDate(Date date, String format) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        Date parsedDate = fmt.parse(fmt.format(date));
        java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
        return sqlDate;
    }

    /**
     * Obtains a description of a given period.
     *
     * @param startDate
     *            The start date.
     * @param endDate
     *            The end date.
     * @return Ex: "From 01/05/2013 to 31/05/2013"
     * @author arthemus
     * @since 25/06/2013
     */
    public static String getPeriodDescription(Date startDate, Date endDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String start = format.format(startDate);
        String end = format.format(endDate);
        return "From ".concat(start).concat(" to ").concat(end);
    }

    /**
     * Obtains a full date description with day of the week, month, and year.
     *
     * @param date
     *            The date to format.
     * @return The formatted date string.
     */
    public static String getDateInFull(Date date) {
        DateFormat dfmt = new SimpleDateFormat("EEEE, MMMM d, yyyy");
        return dfmt.format(date);
    }

    /**
     * Obtains the current date in full.
     *
     * @return The formatted current date string.
     */
    public static String getDateInFull() {
        return Functions.getDateInFull(new Date());
    }

    /**
     * Obtains the time of a given date.
     *
     * @param date
     *            The date to extract the time from.
     * @return The time string in HH:mm:ss format.
     */
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String currentTime = format.format(date);
        return currentTime;
    }

    /**
     * Obtains the current time.
     *
     * @return The current time string in HH:mm:ss format.
     */
    public static String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String currentTime = format.format(new Date());
        return currentTime;
    }

    /**
     * Helper for loading a file via stream.
     *
     * @param resource
     *            The resource path.
     * @return Stream of a given file.
     * @author Arthemus C. Moreira
     */
    public static InputStream getInputStream(String resource) {
        String stripped = resource.startsWith("/") ? resource.substring(1) : resource;
        InputStream stream = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null)
            stream = classLoader.getResourceAsStream(stripped);
        if (stream == null)
            stream = Functions.class.getResourceAsStream(resource);
        if (stream == null)
            stream = Functions.class.getClassLoader().getResourceAsStream(stripped);
        if (stream == null)
            throw new RuntimeException(resource.concat(" not found"));
        return stream;
    }

    /**
     * Obtains a date without the time description.
     *
     * @param date
     *            The date to strip the time from.
     * @return dd/MM/yyyy 00:00:00
     */
    public static Date getOnlyDate(Date date) {
        Date result = null;
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                String temp = format.format(date);
                result = format.parse(temp);
            } catch (ParseException e) {
                throw new RuntimeException("Failed to remove the time from the date: " + date);
            }
        }
        return result;
    }

    /**
     * Obtains the current date without the time description.
     *
     * @return dd/MM/yyyy 00:00:00
     */
    public static Date getOnlyDate() {
        return Functions.getOnlyDate(new Date());
    }

    /**
     * Rewrites a given monetary value with leading zeros for correct
     * formatting.
     *
     * @param value
     *            The value to be formatted.
     * @param digits
     *            The number of digits to pad to.
     * @return The new formatted value.
     * @author Arthemus C. Moreira
     */
    public static String padLeftZeros(final String value, final int digits) {
        StringBuilder builder = new StringBuilder(digits);
        int length = (digits - value.length());
        for (int iCount = 0; iCount < length; iCount++)
            builder.append("0");
        builder.append(value);
        return builder.toString();
    }

    /**
     * Checks if a given value exists, preventing the well-known
     * NullPointerException.
     *
     * @param value
     *            The value to check.
     * @return true or false
     * @author arthemus
     */
    public static boolean isExists(String value) {
        return (value != null && !value.isEmpty());
    }

    /**
     * Returns a regular expression based on the prepositions.
     *
     * @return A regular expression string.
     * @author Arthemus C. Moreira
     */
    static String getPrepositionsRegex() {
        final int length = PREPOSITIONS.length;
        StringBuilder builder = new StringBuilder(length * 2);
        for (byte count = 0; count < length; count++) {
            if (count == 0)
                builder.append("[");
            if (count > 0)
                builder.append("-");
            builder.append(PREPOSITIONS[count]);
            if ((count + 1) == length)
                builder.append("]");
        }
        return builder.toString();
    }

    /**
     * Removes prepositions from a given phrase.
     *
     * @param reference
     *            The phrase to clean.
     * @return The cleaned phrase.
     * @author Arthemus C. Moreira
     */
    public static String removePrepositions(final String reference) {
        LinkedList<String> referList = new LinkedList<String>(Arrays.asList(reference.split(" ")));
        CollectionUtils.filter(referList, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                boolean result = false;
                for (String item : PREPOSITIONS) {
                    result = ((String) object).equalsIgnoreCase(item);
                    if (result) break;
                }
                return !result;
            }
        });
        StringBuilder builder = new StringBuilder(reference.length());
        for (String item : referList)
            builder.append(item).append(" ");
        return builder.toString().trim();
    }

    /**
     * Removes characters from a string such as hyphens, underscores, periods,
     * and commas, leaving only letters A-Z and numbers 0-9.
     * <p>
     * The regular expression [^0-9A-Za-z] identifies everything that is
     * not an alphanumeric character.
     *
     * @param value
     *            The value to clean.
     * @return The cleaned string.
     */
    public static String removeSpecialCharacters(String value) {
        if (!Functions.isExists(value))
            return "";
        return value.replaceAll("[^0-9A-Za-z]", "").trim();
    }

    /**
     * Helper for truncating string values.
     *
     * @param value
     *            The value to be truncated.
     * @param init
     *            The start character index.
     * @param end
     *            The end character index.
     * @return The truncated string.
     */
    private static String getTruncate(final String value, final int init, final int end) {
        if (value == null) return new String();
        String result = value;
        if (result.length() >= end)
            result = value.substring(init, end);
        return result;
    }

    /**
     * Truncates, from right to left, a given string returning a smaller
     * value relative to its actual size.
     *
     * @param value
     *            The value to truncate.
     * @param length
     *            The desired length.
     * @return The truncated string.
     */
    public static String getTruncateRightToLeft(final String value, final int length) {
        return Functions.getTruncate(value, 0, length);
    }

    /**
     * Truncates, from left to right, a given string returning a smaller
     * value relative to its actual size.
     *
     * @param value
     *            The value to truncate.
     * @param length
     *            The desired length.
     * @return The truncated string.
     */
    public static String getTruncateLeftToRight(final String value, final int length) {
        int init = value.length() - length;
        if (init < 0) init = init * -1;
        return Functions.getTruncate(value, init, value.length());
    }

    /**
     * Prints the contents of a collection of strings, breaking a line at
     * each set of characters, forming a vertical list.
     *
     * @param list
     *            The collection of strings to print.
     * @return The vertical list as a string.
     */
    public static String printListVertical(Collection<String> list) {
        StringBuilder result = new StringBuilder(list.size() * 2);
        for (String item : list) {
            result.append(item);
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * Obtains the internet IP address.
     *
     * @return The external IP address.
     */
    public static String getExternalIP() {
        URL whatismyip = null;
        try {
            whatismyip = new URL("http://checkip.amazonaws.com");
        } catch (MalformedURLException e) {
            LOGGER.error("Failed to construct URL for external IP lookup", e);
        }
        return Functions.getHttpContent(whatismyip);
    }

    /**
     * Obtains the content of a web page based on its URL.
     *
     * @param url
     *            The URL to fetch.
     * @return The content of the web page.
     */
    public static String getHttpContent(URL url) {
        String content = new String();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            content = in.readLine();
            in.close();
        } catch (IOException e) {
            LOGGER.error("Failed to retrieve HTTP content from URL: {}", url, e);
        }
        return content;
    }

    /**
     * Obtains a map of values commonly used by frameworks as parameters.
     * <p>
     * Examples of the use of this type of map can be seen in Hibernate,
     * in searches using NamedQueries and in JasperReports, where a map
     * of values is used to display values in the report and execute
     * internal queries.
     *
     * @param array
     *            Ex: Object[][] array = { {"key1", new Object()}, {"key2", new Object()} };
     * @return A map of key-value pairs.
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> getParameters(Object[] array) {
        return MapUtils.putAll(new HashMap<K, V>(), array);
    }

    /**
     * Converts a date string in dd/MM/yyyy format to a Date.
     *
     * @param dateString
     *            The date string to parse.
     * @return The parsed Date.
     * @throws ParseException
     */
    public static Date getDateBy(String dateString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(dateString);
    }

    /**
     * Converts a number to the specified number of decimal places.
     *
     * @param number
     *            The number to round.
     * @param decimalPlaces
     *            The number of decimal places.
     * @return The rounded number with the specified decimal places.
     */
    public static Double roundTo(Double number, int decimalPlaces) {
        if (decimalPlaces < 0)
            throw new ArithmeticException("The decimal places must be equal to or greater than 0 (zero).");

        Double numberDivider = Math.pow(10, decimalPlaces);
        Double resultNumber = number * numberDivider;

        return Math.round(resultNumber) / numberDivider;
    }

    public static Date getFutureWorkDate(int days) {
        if (days < 1)
            return new Date();
        LocalDate localDate = Instant.ofEpochMilli(new Date().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        int addedDays = 0;
        while (addedDays < days) {
            localDate = localDate.plusDays(1);
            if (!(localDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                localDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                ++addedDays;
            }
        }
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
