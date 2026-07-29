package org.autonomous.functions;

import java.util.Date;
import java.util.HashMap;

/**
 * Analyzes whether a given vehicle is or will be under driving restriction
 * based on its license plate and a specific date.
 *
 * @author arthemus
 * @since 11/07/2013
 * @see Week
 */
public class MunicipalDrivingRestriction {

	private final String licensePlate;

	private static final HashMap<Week, int[]> drivingRestriction = new HashMap<Week, int[]>(5);

	static {
		drivingRestriction.put(Week.MONDAY, new int[] { 1, 2 });
		drivingRestriction.put(Week.TUESDAY, new int[] { 3, 4 });
		drivingRestriction.put(Week.WEDNESDAY, new int[] { 5, 6 });
		drivingRestriction.put(Week.THURSDAY, new int[] { 7, 8 });
		drivingRestriction.put(Week.FRIDAY, new int[] { 9, 0 });
	}

	/**
	 * License plate to be analyzed for its driving restriction day.
	 *
	 * @param licensePlate
	 */
	public MunicipalDrivingRestriction(final String licensePlate) {
		this.licensePlate = licensePlate;
	}

	/**
	 * Checks whether a given license plate is under driving restriction on
	 * the current date.
	 *
	 * @return true if the vehicle is under restriction today, false otherwise.
	 */
	public boolean isInRestrictionToday() {
		return isInRestrictionOn(new Date());
	}

	/**
	 * Checks whether a given license plate is under driving restriction on
	 * a specific date.
	 *
	 * @param date
	 *            The date to check.
	 * @return true if the vehicle is under restriction on the given date,
	 *         false otherwise.
	 */
	public boolean isInRestrictionOn(Date date) {
		int lastDigit = Integer.parseInt(licensePlate.substring(licensePlate.length() - 1));
		int[] endings = drivingRestriction.get(Week.getDay(date));
		for (int item : endings)
			if (item == lastDigit)
				return Boolean.TRUE;
		return Boolean.FALSE;
	}
}
