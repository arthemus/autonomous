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
	private Crypto _crypto;
	
	@Before
	public void setUp() throws Exception{
		_crypto = new Crypto();
	}
	
	private String getPrivateKey(){
		try {
			return _crypto.getPrivateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Test
	public void testeKeyGenerator(){
		
		try {
			String key = _crypto.getPrivateKey();
			
			Assert.assertTrue(key != null);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeEncrypt(){
		String strKey = getPrivateKey();
		String message = "001DALAM DISTRIBUIDORA DE FERRO E ACO LTDADALAMRUA IPEUVASAO PAULODALAMSP14819738411310142626000196378199331125019791";
		String strDecript = null;
		String msgCript = null;
		
		try {
			msgCript = _crypto.doEncript(strKey, message);
			
			strDecript = _crypto.doDecript(strKey, msgCript);
		} catch (CryptoException e) {
			e.printStackTrace();
		}
		
		Assert.assertEquals(message, strDecript);
	}

}
