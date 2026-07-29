package org.autonomous.functions;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the MunicipalDrivingRestriction class.
 *
 * @author arthemus
 * @since 11/07/2013
 */
public class MunicipalDrivingRestrictionTest {

	@Test
	public void shouldDetectDrivingRestrictionOnGivenDate() {
		MunicipalDrivingRestriction restriction = new MunicipalDrivingRestriction("BNQ-3051");
		try {
			Assert.assertEquals(Boolean.TRUE, restriction
					.isInRestrictionOn(new SimpleDateFormat("dd/MM/yyyy").parse("24/08/1987")));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
}
