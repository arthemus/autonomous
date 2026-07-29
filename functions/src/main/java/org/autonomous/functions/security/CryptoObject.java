package org.autonomous.functions.security;

import com.google.gson.Gson;

/**
 * This class handles the encryption of entire objects. For encryption, the
 * object is first serialized to JSON and then the information is encrypted.
 * When decrypting the message, the system also deserializes the JSON,
 * converting it back to the respective object.
 *
 * @author Walter Portugal
 *
 * @param <T>
 * @see Crypto
 */

public class CryptoObject<T> {

	private final Crypto crypto;
	private final String privateKey;

	public CryptoObject(String privateKey) throws CryptoException {
		crypto = new Crypto();
		this.privateKey = privateKey;
	}

	/**
	 * Returns an encrypted string representing the object passed as a
	 * parameter.
	 *
	 * @param object
	 *            The object to encrypt.
	 * @return The encrypted string.
	 * @throws CryptoException
	 */
	public String doEncrypt(final T object) throws CryptoException {

		Gson gson = new Gson();
		String strObjectJson = gson.toJson(object);

		return crypto.doEncrypt(privateKey, strObjectJson);
	}

	/**
	 * Decrypts a string into an object.
	 *
	 * @param strEncrypted
	 *            The encrypted string to decrypt.
	 * @param classReference
	 *            The class to deserialize into.
	 * @return The decrypted object.
	 * @throws CryptoException
	 */
	public T doDecrypt(String strEncrypted, Class<T> classReference) throws CryptoException {

		try {
			String strJson = crypto.doDecrypt(privateKey, strEncrypted);

			Gson gson = new Gson();

			return gson.fromJson(strJson, classReference);
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}
}
