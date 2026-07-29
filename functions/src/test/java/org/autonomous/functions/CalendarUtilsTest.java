package org.autonomous.functions;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;


public class CalendarUtilsTest {

	@Test
	public void shouldAddDaysToCurrentDate() {
		CalendarUtils c = CalendarUtils.getInstance().addDaysToCurrentDate(30);

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 30);

		Assert.assertEquals(Functions.getOnlyDate(calendar.getTime()), c.getDate());
	}

	@Test
	public void shouldSubtractDaysFromCurrentDate() {
		CalendarUtils c = CalendarUtils.getInstance().subtractDaysFromCurrentDate(20);

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -20);

		Assert.assertEquals(Functions.getOnlyDate(calendar.getTime()), c.getDate());
	}

	@Test
	public void shouldSubtractDaysFromDate() throws ParseException {

		CalendarUtils c = CalendarUtils.getInstance().subtractDays(Functions.getDateBy("02/05/2014"), 20);

		Assert.assertEquals("12/04/2014", c.toString());
	}

	@Test
	public void shouldAddDaysToDate() throws ParseException {
		CalendarUtils c = CalendarUtils.getInstance().addDays(Functions.getDateBy("02/05/2014"), 20);

		Assert.assertEquals("22/05/2014", c.toString());
	}

}
