package org.autonomous.functions;

import java.util.Calendar;
import java.util.Date;

/**
 * Class for performing the sum of micro time periods such as overtime and
 * time credits.
 *
 * The idea is to be able to calculate the difference between time periods,
 * for example, between 10:30:00 and 18:00:00 there is a period of 8 hours
 * and 30 minutes.
 *
 * It is not yet possible to add times that exceed 24 hours, for example, a
 * string like 43:00:00 representing a time of 43 hours. In the future, this
 * class will be improved to support the calculation of larger periods such
 * as days, months, and years.
 *
 * An alternative to this class would be to work with the JodaTime API:
 * {@link http://joda-time.sourceforge.net/}
 *
 * @author arthemus
 * @since 15/07/2013
 * @see Time
 * @see TimeUtils
 */
public class TimeCalculator {

	private final Calendar calendar;

	public TimeCalculator() {
		Time temp = Time.getInstance(new Date(), "00:00:00");
		this.calendar = temp.getGregorianCalendar();
		long millis = this.calendar.getTimeInMillis() - temp.time;
		this.calendar.setTimeInMillis(millis);
	}

	public TimeCalculator add(final Time time) {
		calendar.add(Calendar.HOUR_OF_DAY, time.getHour());
		calendar.add(Calendar.MINUTE, time.getMinute());
		calendar.add(Calendar.SECOND, time.getSecond());
		return this;
	}

	public TimeCalculator subtract(final Time time) {
		calendar.add(Calendar.HOUR_OF_DAY, time.getHour() * -1);
		calendar.add(Calendar.MINUTE, time.getMinute() * -1);
		calendar.add(Calendar.SECOND, time.getSecond() * -1);
		return this;
	}

	public String getResult() {
		long millis = calendar.getTimeInMillis();
		return Time.toStringOffDays(millis);
	}
}
