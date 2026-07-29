package org.autonomous.functions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * Class for controlling time, hours, minutes, and seconds.
 *
 * @author Arthemus C. Moreira
 * @since 16/01/2013
 */
public final class Time {

	private static final String TIME_FORMAT = "HH:mm:ss";

	/**
	 * Time constants.
	 */
	public static final int SECOND = 1000;
	public static final int MINUTE = (60 * SECOND);
	public static final int HOUR = (60 * MINUTE);
	public static final int DAY = (24 * HOUR);

	/**
	 * Original format of a time, Hour : Minute : Second.
	 * For example: 23:45:21
	 */
	public static final SimpleDateFormat FORMAT = new SimpleDateFormat(TIME_FORMAT);

	/**
	 * Obtains a differential of the time.
	 *
	 * @param millis
	 *            Time in milliseconds to be calculated.
	 * @param param
	 *            DAY, HOUR, MINUTE, or SECOND.
	 * @return The value present in the time.
	 */
	public static final long get(final long millis, final int param) {
		return (millis / param);
	}

	/**
	 * Converts a millisecond value to a time format, separating hours,
	 * minutes, and seconds but disregarding days.
	 *
	 * @param milliseconds
	 *            The value to be converted.
	 * @return Time string in HH:mm:ss format.
	 */
	public static final String toStringOffDays(final long milliseconds) {

		long millis = milliseconds;

		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);

		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		millis -= TimeUnit.MINUTES.toMillis(minutes);

		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

		final String _sep = ":";

		StringBuilder sb = new StringBuilder(64);
		sb.append(Functions.padLeftZeros(String.valueOf(hours), 2));
		sb.append(_sep);
		sb.append(Functions.padLeftZeros(String.valueOf(minutes), 2));
		sb.append(_sep);
		sb.append(Functions.padLeftZeros(String.valueOf(seconds), 2));

		return sb.toString();
	}

	/**
	 * Converts a period of milliseconds to a time format, separating hours,
	 * minutes, and seconds and considering the days of the period.
	 *
	 * @param milliseconds
	 *            The value to be converted.
	 * @return String in HH:mm:ss format.
	 */
	public static final String toStringWithDays(final long milliseconds) {

		long millis = milliseconds;

		long days = TimeUnit.MILLISECONDS.toDays(millis);
		millis -= TimeUnit.DAYS.toMillis(days);

		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);

		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		millis -= TimeUnit.MINUTES.toMillis(minutes);

		long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

		final String _sep = ":";

		StringBuilder sb = new StringBuilder(64);
		sb.append(Functions.padLeftZeros(String.valueOf(hours), 2));
		sb.append(_sep);
		sb.append(Functions.padLeftZeros(String.valueOf(minutes), 2));
		sb.append(_sep);
		sb.append(Functions.padLeftZeros(String.valueOf(seconds), 2));

		return sb.toString();
	}

	/**
	 * Time defined in milliseconds.
	 */
	public final long time;

	public static final Time getInstance() {
		return new Time(new Date());
	}

	public static final Time getInstance(final long dateInMilliseconds) {
		return new Time(dateInMilliseconds);
	}

	public static final Time getInstance(final Date date) {
		return new Time(date);
	}

	public static final Time getInstance(final Date baseDate, final String baseTime) {
		return new Time(Functions.getOnlyDate(baseDate), baseTime);
	}

	private Time(final Date date) {
		this.time = date.getTime();
	}

	private Time(final long time) {
		this.time = time;
	}

	private Time(final Date baseDate, final String baseTime) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(baseDate);
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(baseTime.substring(0, 2)));
		calendar.set(Calendar.MINUTE, Integer.parseInt(baseTime.substring(3, 5)));
		calendar.set(Calendar.SECOND, Integer.parseInt(baseTime.substring(6, 8)));
		this.time = calendar.getTimeInMillis();
	}

	/**
	 * Decreases the hour.
	 *
	 * @param amount
	 *            The number of hours to be removed from the time.
	 * @return A new Time instance.
	 */
	public Time subtractHours(final int amount) {
		validateNonNegative(amount);
		return addHours(amount * -1);
	}

	/**
	 * Decreases the minutes.
	 *
	 * @param amount
	 *            The number of minutes to be removed from the time.
	 * @return A new Time instance.
	 */
	public Time subtractMinutes(final int amount) {
		validateNonNegative(amount);
		return addMinutes(amount * -1);
	}

	/**
	 * Returns a Calendar object based on the base time.
	 *
	 * @return A Calendar instance.
	 */
	public Calendar getGregorianCalendar() {
		Calendar current = new GregorianCalendar();
		current.setTimeInMillis(time);
		return (Calendar) current.clone();
	}

	/**
	 * Hour of the time.
	 *
	 * @return If time = 12:35:23, returns 12.
	 */
	public int getHour() {
		Calendar current = getGregorianCalendar();
		return current.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Minutes of the time.
	 *
	 * @return If time = 12:35:23, returns 35.
	 */
	public int getMinute() {
		Calendar current = getGregorianCalendar();
		return current.get(Calendar.MINUTE);
	}

	/**
	 * Seconds of the time.
	 *
	 * @return If time = 12:35:23, returns 23.
	 */
	public int getSecond() {
		Calendar current = getGregorianCalendar();
		return current.get(Calendar.SECOND);
	}

	/**
	 * Milliseconds of the time.
	 *
	 * @return The millisecond component of the time.
	 */
	public int getMillisecond() {
		Calendar current = getGregorianCalendar();
		return current.get(Calendar.MILLISECOND);
	}

	/**
	 * Obtains a random time based on a divergence.
	 * Ex: Divergence = 15 (15 minutes)
	 * Time = 15:30:12
	 * Returns a value between 15:15:12 and 15:45:12
	 *
	 * @param divergence
	 *            The difference between the current time and the new time.
	 * @return A new random time string.
	 */
	public String getRandomTime(final int divergence) {

		if (divergence < 0)
			throw new RuntimeException("The tolerance value must be greater than zero.");

		/*
		 * If the tolerance is 5 for example, what are the numbers that
		 * make up the two poles of 5, positive and negative?
		 * -5 -4 -3 -2 -1 0 1 2 3 4 5
		 * which equals 11 elements:
		 * Formula = (5 * 2) + 1
		 */
		int rand = (int) (Math.random() * (divergence * 2) + 1);

		int minute = divergence - rand;

		Date date = new Date(time);

		Calendar current = new GregorianCalendar();

		// Obtains the time from the configured date...
		current.setTime(date);

		// Adds the new amount of time...
		current.add(Calendar.MINUTE, minute);

		return FORMAT.format(current.getTime());
	}

	/**
	 * Checks whether the other time is greater than the time of this
	 * instance.
	 *
	 * @param otherTime
	 *            The time to compare against.
	 * @return True or False.
	 */
	public boolean isGreaterThan(final Time otherTime) {

		Calendar calendarBase = getGregorianCalendar();

		Calendar calendarExp = new GregorianCalendar();
		calendarExp.setTimeInMillis(otherTime.time);

		/**
		 * Base > Exp = 1
		 * Base == Exp = 0
		 * Base < Exp = -1
		 */
		int result = calendarBase.compareTo(calendarExp);

		if (result == 1)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	/**
	 * Increases the hour value of the current time.
	 *
	 * @param amount
	 *            The number of hours to add.
	 * @return A new Time instance.
	 */
	public Time addHours(final int amount) {
		Calendar current = getGregorianCalendar();
		current.add(Calendar.HOUR_OF_DAY, amount);
		return new Time(current.getTime());
	}

	/**
	 * Increases the minute value of the current time.
	 *
	 * @param amount
	 *            The number of minutes to add.
	 * @return A new Time instance.
	 */
	public Time addMinutes(final int amount) {
		Calendar current = getGregorianCalendar();
		current.add(Calendar.MINUTE, amount);
		return new Time(current.getTime());
	}

	private void validateNonNegative(final int value) {
		if (value < 0)
			throw new NumberFormatException("The value cannot be negative");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (time ^ (time >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Time other = (Time) obj;
		if (time != other.time)
			return false;
		return true;
	}

	@Override
	public String toString() {
		Calendar temp = getGregorianCalendar();
		return FORMAT.format(temp.getTime());
	}
}
