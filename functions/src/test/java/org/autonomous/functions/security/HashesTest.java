package org.autonomous.functions.security;

import org.junit.Assert;
import org.junit.Test;

public class HashesTest {


	@Test
	public void shouldGenerateMd5Hash() {

		String hash = Hashes.md5("CPMACERTPECA");

		Assert.assertEquals(hash, "2c598f8f78b5dd4b2f9a76a33916d6a8");
	}

	@Test
	public void shouldGenerateMd5HashWithSalt() {

		String hash = Hashes.md5("CPM", "ACERTPECA");

		Assert.assertEquals(hash, "2c598f8f78b5dd4b2f9a76a33916d6a8");
	}

}
