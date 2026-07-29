package org.autonomous.functions;

/**
 * Utilities for working with times.
 *
 * @author arthemus
 * @since 12/07/2013
 * @see Time
 * @see TimeCalculator
 */
public class TimeUtils {

	/**
	 * Calculates the difference between times, helping to verify how many
	 * hours were spent between specific time markers.
	 *
	 * @param startTime
	 *            The start time of the period.
	 * @param endTime
	 *            The end time of the period.
	 * @return A reference time. The return of this method should not be
	 *         treated as a specific "time" of day but rather as a
	 *         "quantity" of hours calculated within a period.
	 */
	public static String between(final Time startTime,
			final Time endTime) {
		long millis = (endTime.time - startTime.time);
		return Time.toStringOffDays(millis);
	}

	/**
	 * Returns a new instance of the TimeCalculator class to add or
	 * subtract times.
	 *
	 * @return A new instance of the {@link TimeCalculator} class.
	 */
	public static TimeCalculator calculate() {
		return new TimeCalculator();
	}
}
