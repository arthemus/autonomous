package org.autonomous.functions;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the NullSafe class.
 *
 * @author arthemus
 * @since 07/02/2014
 */
public class NullSafeTest {

	@Test
	public void shouldReturnNotNullForNullString() {
		Assert.assertNotNull(NullSafe.stringNotNull(null));
	}

	@Test
	public void shouldReturnNotNullForNullByte() {
		Assert.assertNotNull(NullSafe.byteNotNull(null));
	}

	@Test
	public void shouldReturnNotNullForNullShort() {
		Assert.assertNotNull(NullSafe.shortNotNull(null));
	}

	@Test
	public void shouldReturnNotNullForNullInteger() {
		Assert.assertNotNull(NullSafe.intNotNull((Integer) null));
	}

	@Test
	public void shouldReturnNotNullForNullLong() {
		Assert.assertNotNull(NullSafe.longNotNull(null));
	}

	@Test
	public void shouldReturnNotNullForNullFloat() {
		Assert.assertNotNull(NullSafe.floatNotNull(null));
	}

	@Test
	public void shouldReturnNotNullForNullDouble() {
		Assert.assertNotNull(NullSafe.doubleNotNull(null));
	}

}
