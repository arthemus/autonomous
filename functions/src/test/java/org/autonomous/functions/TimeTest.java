package org.autonomous.functions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for time control.
 *
 * @author arthemus
 * @since 12/07/2013
 * @see Time
 */
public class TimeTest {

	@Test
	public void shouldObtainTimeFromDate() {
		try {
			Time result = Time.getInstance(new SimpleDateFormat("dd/MM/yyyy").parse("24/08/1987"));
			Assert.assertEquals("00:00:00", result.toString());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void shouldCompareHour() {
		Time time = Time.getInstance();
		Calendar calendar = new GregorianCalendar();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		boolean result = (time.getHour() == hour);
		Assert.assertTrue(
				"The hours diverge: Time = " + time.getHour()
						+ ", Calendar = " + hour, result);
	}

	@Test
	public void shouldCompareMinute() {
		Time time = Time.getInstance();
		Calendar calendar = new GregorianCalendar();
		int minute = calendar.get(Calendar.MINUTE);
		boolean result = (time.getMinute() == minute);
		Assert.assertTrue(
				"The minutes diverge: Time = " + time.getMinute()
						+ ", Calendar = " + minute, result);
	}

	@Test
	public void shouldGenerateRandomTimeDifferentFromCurrent() {
		Time original = Time.getInstance();
		String random = original.getRandomTime(10);
		boolean result = (original.equals(random));
		Assert.assertFalse("The times are equal: " + original + " - "
				+ random, result);
	}

	@Test
	public void shouldCompareTimeBeforeMidnight() {
		Date referenceDate = new Date();
		Time earlier = Time.getInstance(referenceDate, "22:21:15");
		Time later = Time.getInstance(referenceDate, "00:00:11");
		boolean result = earlier.isGreaterThan(later);
		Assert.assertTrue(
				"The time " + later + " is less than " + earlier, result);
	}

	@Test
	public void shouldCompareTimeAfterMidnight() {
		Date referenceDate = new Date();
		Time earlier = Time.getInstance(referenceDate, "00:21:15");
		Time later = Time.getInstance(referenceDate, "02:00:11");
		boolean result = later.isGreaterThan(earlier);
		Assert.assertTrue("The time " + later + " is greater than " + earlier, result);
	}

	@Test
	public void shouldVerifyCurrentTimeIsGreaterThanPrevious() {
		Time previous = Time.getInstance();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		Time current = Time.getInstance();
		boolean result = current.isGreaterThan(previous);
		Assert.assertTrue("The current time " + current + " is greater than previous "
				+ previous, result);
	}

	@Test
	public void shouldCalculateTimeDifferential() {
		Date referenceDate = new Date();
		Time arrival = Time.getInstance(referenceDate, "11:00:00");
		Time departure = Time.getInstance(referenceDate, "17:30:00");
		String calculatedTime = TimeUtils.between(arrival, departure);
		Assert.assertEquals("06:30:00", calculatedTime);
	}

	@Test
	public void shouldCompareManuallyFormedTime() {
		String time = "15:12:31";
		Time manual = Time.getInstance(new Date(), time);
		Assert.assertEquals(time, manual.toString());
	}

	@Test
	public void shouldAddHours() {
		Time previous = Time.getInstance(new Date(), "13:00:00");
		Time updated = previous.addHours(2);
		Assert.assertEquals("15:00:00", updated.toString());
	}

	@Test
	public void shouldSumVariousTimesWithZeroBase() {
		Date referenceDate = new Date();
		String time = TimeUtils.calculate()
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.add(Time.getInstance(referenceDate, "01:30:00"))
				.add(Time.getInstance(referenceDate, "02:45:00"))
				.add(Time.getInstance(referenceDate, "23:15:12"))
				.getResult();
		Assert.assertEquals("53:30:12", time);
	}

	@Test
	public void shouldSumVariousTimesWithDefinedBase() {
		Date referenceDate = new Date();
		String time1 = TimeUtils.calculate()
				.add(Time.getInstance(referenceDate, "03:00:00"))
				.getResult();
		Assert.assertEquals("03:00:00", time1);
		String time2 = new TimeCalculator()
				.add(Time.getInstance(referenceDate, "23:00:00"))
				.add(Time.getInstance(referenceDate, "01:00:00"))
				.getResult();
		Assert.assertEquals("24:00:00", time2);
	}

	@Test
	public void shouldSubtractVariousTimesWithDefinedBase() {
		Date referenceDate = new Date();
		String time = TimeUtils.calculate()
				.add(Time.getInstance(referenceDate, "10:00:00"))
				.subtract(Time.getInstance(referenceDate, "01:00:00"))
				.getResult();
		Assert.assertEquals("09:00:00", time);
	}

}
