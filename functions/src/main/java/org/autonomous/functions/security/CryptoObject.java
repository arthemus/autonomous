package org.autonomous.functions.security;

import com.google.gson.Gson;

/**
 * Essa classe contempla a criptografia de objetos inteiros. Para a criptografia é feito, inicialmente, a serialização
 * do objeto para JSON e posteriormente a criptografia das informações. Ao descriptografar a mensagem, o sistema também
 * desserializa o JSON convertendo ao respectivo objeto.
 * 
 * @author Walter Portugal
 *
 * @param <T>
 * @see Crypto
 */

public class CryptoObject<T> {

	private final Crypto _crypto;
	private final String _privateKey;
	
	public CryptoObject(String privateKey) throws CryptoException{
		_crypto = new Crypto();
		_privateKey = privateKey;
	}
	
	/**
	 * Retorna uma string criptografada referente ao objeto passado como parâmetro.
	 * 
	 * @param object
	 * @return
	 * @throws CryptoException 
	 */	
	public String doEncrypt(final T object) throws CryptoException{
		
		Gson gson = new Gson();
		String strObjectJson = gson.toJson(object);
		
		return _crypto.doEncript(_privateKey, strObjectJson);
	}
	
	/**
	 * Descriptografa em um objeto uma string.
	 * 
	 * @param strEncrypted
	 * @param classReference
	 * @return
	 * @throws CryptoException 
	 */
	public T doDecript(String strEncrypted, Class<T> classReference) throws CryptoException{
		
		try{
			String strJson = _crypto.doDecript(_privateKey, strEncrypted);
			
			Gson gson = new Gson();
			
			return gson.fromJson(strJson, classReference);
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}
}
