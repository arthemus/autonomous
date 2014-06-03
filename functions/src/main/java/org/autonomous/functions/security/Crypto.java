package org.autonomous.functions.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.xerces.impl.dv.util.Base64;

/**
 * Classe desenvolvida para ser utilizada na criptografia dos dados do ERP referente a controle de
 * licenças.
 * 
 * @author Walter Portugal
 *
 */

public class Crypto {
	
	private Cipher _cipher;
	private final String deadBeef = "deadbeefs0ftland";
	
	public Crypto() throws CryptoException{
		Initilize();
	}

	private final void Initilize() throws CryptoException {
		try {
			_cipher = getCipher();
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoException(e);
		} catch (NoSuchProviderException e) {
			throw new CryptoException(e);
		} catch (NoSuchPaddingException e) {
			throw new CryptoException(e);
		}
	}

	private Cipher getCipher() throws NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException {
		return Cipher.getInstance("AES/ECB/PKCS5Padding");
	}

	@SuppressWarnings("unused")
	private Key getStringToSecretKey(String secretKey){
		byte[] encodeKey = Base64.decode(secretKey);		
		return new SecretKeySpec(encodeKey, "AES");
	}
	
	private byte[] wrapPrivateKey(Key key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, 
					UnsupportedEncodingException{
		
		byte[] bytesDeadBeef = deadBeef.getBytes("UTF8");
		
		_cipher.init(Cipher.WRAP_MODE, new SecretKeySpec(bytesDeadBeef, "AES"));
		
		return _cipher.wrap(key);
	}
	
	private Key unwrapPrivateKey(byte[] privateKey) throws UnsupportedEncodingException, InvalidKeyException, 
					NoSuchAlgorithmException{
		byte[] bytesDeadBeef = deadBeef.getBytes("UTF8");
		
		_cipher.init(Cipher.UNWRAP_MODE, new SecretKeySpec(bytesDeadBeef, "AES"));
		
		return _cipher.unwrap(privateKey, "AES", Cipher.SECRET_KEY);
	}
	
	/**
	 * Método a ser utilizado para gerar a chave privada do cliente. Essa chave deverá ser armazenada
	 * e utilizada para criptografia das outras informações. É gerada apenas no cadastro do cliente.
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 * @throws UnsupportedEncodingException 
	 */
	
	public String getPrivateKey() throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, 
					BadPaddingException, UnsupportedEncodingException{
		
		KeyGenerator generator = KeyGenerator.getInstance("AES");		
		
		generator.init(new SecureRandom());
		
		Key key = generator.generateKey();	
		
		//Retorna a chave ofuscando seu real valor através do wrapkey.
		return Base64.encode(wrapPrivateKey(key));
	}
	
	/**
	 * A partir de uma chave privada, que deve ser gerada pelo método getPrivateKey, esse método
	 * criptografa uma string qualquer - Criptografia AES.
	 * 
	 * 
	 * @param privateKey
	 * @param message
	 * @return
	 * @throws CryptoException 
	 * 
	 * @see getPrivateKey()
	 */
	
	public String doEncript(String privateKey, String message) throws CryptoException {
		
		try {
			Key key = unwrapPrivateKey(Base64.decode(privateKey));	
			
			_cipher.init(Cipher.ENCRYPT_MODE, key);
			
			byte[] messageBytes = message.getBytes("UTF8");
			
			byte[] raw = _cipher.doFinal(messageBytes);
			
			return Base64.encode(raw);
			
		} catch (InvalidKeyException e) {
			throw new CryptoException(e);
		} catch (UnsupportedEncodingException e) {
			throw new CryptoException(e);
		} catch (IllegalBlockSizeException e) {
			throw new CryptoException(e);
		} catch (BadPaddingException e) {
			throw new CryptoException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoException(e);
		}
	}
	
	/**
	 * Esse método descriptografa uma mensagem criptografada com o médoto doEncrypt. É importante observar que a
	 * mesma chave usada para criptografar obtida a partir do médoto getPrivateKey deve ser usada para a descriptografia. 
	 * 
	 * @param privateKey
	 * @param strEncript
	 * @return
	 * @throws CryptoException 
	 * 
	 * @see doEncript, getPrivateKey
	 */	
	public String doDecript(String privateKey, String strEncript) throws CryptoException{
		
		String strDecript = null;
		
		try {
			
			Key key = unwrapPrivateKey(Base64.decode(privateKey));	
			
			_cipher.init(Cipher.DECRYPT_MODE, key);
			
			byte[] raw = Base64.decode(strEncript);
			byte[] stringBytes = _cipher.doFinal(raw);
					
			strDecript = new String(stringBytes, "UTF8");
			
		} catch (InvalidKeyException e) {
			throw new CryptoException(e);
		} catch (IllegalBlockSizeException e) {
			throw new CryptoException(e);
		} catch (BadPaddingException e) {
			throw new CryptoException(e);
		} catch (UnsupportedEncodingException e) {
			throw new CryptoException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoException(e);
		}

		return strDecript;
	}	
}
