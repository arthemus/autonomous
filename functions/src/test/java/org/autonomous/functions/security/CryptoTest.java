package org.autonomous.functions.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CryptoTest {
	private Crypto crypto;

	@Before
	public void setUp() throws Exception{
		crypto = new Crypto();
	}

	private String getPrivateKey(){
		try {
			return crypto.getPrivateKey();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void shouldGenerateKey(){

		try {
			String key = crypto.getPrivateKey();

			Assert.assertTrue(key != null);

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void shouldEncryptAndDecrypt(){
		String key = getPrivateKey();
		String message = "001DALAM DISTRIBUIDORA DE FERRO E ACO LTDADALAMRUA IPEUVASAO PAULODALAMSP14819738411310142626000196378199331125019791";
		String decrypted = null;
		String encrypted = null;

		try {
			encrypted = crypto.doEncrypt(key, message);

			decrypted = crypto.doDecrypt(key, encrypted);
		} catch (CryptoException e) {
			throw new RuntimeException(e);
		}

		Assert.assertEquals(message, decrypted);
	}

}
