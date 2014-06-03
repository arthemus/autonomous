package org.autonomous.functions.security;

import org.junit.Assert;
import org.junit.Test;

public class HashsTest {
	
	
	@Test
	public void testCriptografiaMd5() {
		
		String strHash = Hashs.md5("CPMACERTPECA");
		
		Assert.assertEquals(strHash, "2c598f8f78b5dd4b2f9a76a33916d6a8");
	}
	
	@Test
	public void testCriptografiaMd5ComSalt() {
		
		String strHash = Hashs.md5("CPM", "ACERTPECA");
		
		Assert.assertEquals(strHash, "2c598f8f78b5dd4b2f9a76a33916d6a8");
	}

}
