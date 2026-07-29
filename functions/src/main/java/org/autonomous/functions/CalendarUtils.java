package org.autonomous.functions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Class for handling dates - day, month, and year.
 *
 * This class works with the Brazilian date format.
 *
 * @author Walter
 * @since 02/05/2014
 */

public final class CalendarUtils {

	private static final String DATE_FORMAT = "dd/MM/yyyy";

	private final Date _date;

	public static final SimpleDateFormat FORMAT = new SimpleDateFormat(DATE_FORMAT);

	public static final CalendarUtils getInstance() {
		return new CalendarUtils(new Date());
	}

	private CalendarUtils(Date date) {
		_date = date;
	}

	public Date getDate() {
		return Functions.getOnlyDate(_date);
	}

	/**
	 * Calculates a new date by adding days to the current date.
	 *
	 * The result can be obtained from CalendarUtils.getDate().
	 *
	 * @param daysAmount
	 *            The number of days to add.
	 * @return A new CalendarUtils instance.
	 */

	public CalendarUtils addDaysToCurrentDate(int daysAmount) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, daysAmount);

		return new CalendarUtils(calendar.getTime());
	}

	/**
	 * Calculates a new date by subtracting days from the current date.
	 *
	 * The result can be obtained from CalendarUtils.getDate().
	 *
	 * @param daysAmount
	 *            The number of days to subtract.
	 * @return A new CalendarUtils instance.
	 */

	public CalendarUtils subtractDaysFromCurrentDate(int daysAmount) {
		return addDaysToCurrentDate(daysAmount * -1);
	}

	/**
	 * Calculates a new date by adding days to the date passed as a parameter.
	 *
	 * The result can be obtained from CalendarUtils.getDate().
	 *
	 * @param date
	 *            The base date.
	 * @param daysAmount
	 *            The number of days to add.
	 * @return A new CalendarUtils instance.
	 */

	public CalendarUtils addDays(Date date, int daysAmount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, daysAmount);

		return new CalendarUtils(calendar.getTime());
	}

	/**
	 * Calculates a new date by subtracting days from the date passed as a
	 * parameter.
	 *
	 * The result can be obtained from CalendarUtils.getDate().
	 *
	 * @param date
	 *            The base date.
	 * @param daysAmount
	 *            The number of days to subtract.
	 * @return A new CalendarUtils instance.
	 */
	public CalendarUtils subtractDays(Date date, int daysAmount) {
		return addDays(date, daysAmount * -1);
	}

	/**
	 * Returns the date in string format dd/MM/yyyy.
	 */
	@Override
	public String toString() {
		return FORMAT.format(getDate());

	}
}
