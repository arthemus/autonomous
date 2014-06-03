package org.autonomous.functions;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testes unitários para a classe Geleia.
 * 
 * @author arthemus
 * @since 07/02/2014
 */
public class GeleiaTest {

	@Test
	public void testaStringNull() {
		Assert.assertNotNull(Geleia.stringNotNull(null));
	}

	@Test
	public void testaByteNull() {
		Assert.assertNotNull(Geleia.byteNotNull(null));
	}

	@Test
	public void testaShortNull() {
		Assert.assertNotNull(Geleia.shortNotNull(null));
	}

	@Test
	public void testaIntegerNull() {
		Assert.assertNotNull(Geleia.intNotNull(null));
	}

	@Test
	public void testaLongNull() {
		Assert.assertNotNull(Geleia.longNotNull(null));
	}

	@Test
	public void testaFloatNull() {
		Assert.assertNotNull(Geleia.floatNotNull(null));
	}

	@Test
	public void testaDoubleNull() {
		Assert.assertNotNull(Geleia.doubleNotNull(null));
	}

}
