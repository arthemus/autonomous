package org.autonomous.functions;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Utility for managing the days of the week.
 *
 * @author arthemus
 * @since 28/05/2013
 */
public enum Week {

	SUNDAY("Sunday"),

	MONDAY("Monday"),

	TUESDAY("Tuesday"),

	WEDNESDAY("Wednesday"),

	THURSDAY("Thursday"),

	FRIDAY("Friday"),

	SATURDAY("Saturday");

	public static Week getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return Week.getDay(calendar.get(Calendar.DAY_OF_WEEK));
	}

	/**
	 * Returns the day of the week based on an index.
	 *
	 * @author Arthemus C. Moreira
	 * @param index
	 *            Day code obtained, for example, through the
	 *            Calendar.DAY_OF_WEEK constant.
	 * @return Day of the week.
	 */
	public static Week getDay(Integer index) {
		switch (index) {
		case 1:
			return SUNDAY;
		case 2:
			return MONDAY;
		case 3:
			return TUESDAY;
		case 4:
			return WEDNESDAY;
		case 5:
			return THURSDAY;
		case 6:
			return FRIDAY;
		case 7:
			return SATURDAY;
		default:
			return MONDAY;
		}
	}

	public static Week getDay(String index) {
		return Week.getDay(Integer.parseInt(index));
	}

	/**
	 * Returns the current day of the week.
	 *
	 * @author Arthemus C. Moreira
	 * @return Day of the week.
	 */
	public static Week today() {
		Calendar calendar = new GregorianCalendar();
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			return SUNDAY;
		case 2:
			return MONDAY;
		case 3:
			return TUESDAY;
		case 4:
			return WEDNESDAY;
		case 5:
			return THURSDAY;
		case 6:
			return FRIDAY;
		case 7:
			return SATURDAY;
		default:
			return MONDAY;
		}
	}

	private final String description;

	Week(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public Integer getIndex() {
		switch (this) {
		case SUNDAY:
			return 1;
		case MONDAY:
			return 2;
		case TUESDAY:
			return 3;
		case WEDNESDAY:
			return 4;
		case THURSDAY:
			return 5;
		case FRIDAY:
			return 6;
		case SATURDAY:
			return 7;
		default:
			return 2;
		}
	}
}
