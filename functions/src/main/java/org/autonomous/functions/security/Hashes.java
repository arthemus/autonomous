package org.autonomous.functions.security;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * Class responsible for the hash generators and security codes used in the
 * system, from password encryption to names and external parameters.
 *
 * @author Arthemus C. Moreira
 * @since 22/03/2013
 */
public final class Hashes {

	/**
	 * Generator of MD5-encrypted strings.
	 *
	 * @author arthemus
	 * @param value
	 *            The value to be encrypted.
	 * @return The hash string.
	 */
	private static final String doMD5Hash(final String value) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(notNull(value).getBytes(Charset.forName("UTF8")));
			byte[] resultByte = messageDigest.digest();
			return Hex.encodeHexString(resultByte);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 algorithm not available", e);
		}
	}

	/**
	 * MD5 generator with salt.
	 *
	 * @author arthemus
	 * @param value
	 *            The value to be encrypted.
	 * @param salt
	 *            The salt to append.
	 * @return The hash string.
	 */
	private static final String doMD5WithSalt(final String value, final String salt) {
		final String saltedAndHashed = notNull(value) + notNull(salt);
		return doMD5Hash(saltedAndHashed);
	}

	public static final String md5(final String value) {
		return doMD5Hash(value);
	}

	public static final String md5(final String value, final String salt) {
		return doMD5WithSalt(value, salt);
	}

	/**
	 * Prevents null values from being processed by the encoding methods.
	 *
	 * @author arthemus
	 * @param value
	 *            The value to check.
	 * @return The original value or an empty string if null.
	 */
	static final String notNull(String value) {
		return (value == null ? "" : value);
	}

	/**
	 * <pre>
	 * Encodes a string by shifting characters between [A..Z], [a..z].
	 * </pre>
	 *
	 * @author arthemus
	 * @param value
	 *            The value to encode.
	 * @return The encoded value.
	 */
	public static final String rot13(String value) {
		final String temp = notNull(value);
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < temp.length(); i++) {
			char c = temp.charAt(i);
			if (c >= 'a' && c <= 'm') c += 13;
			else if (c >= 'A' && c <= 'M') c += 13;
			else if (c >= 'n' && c <= 'z') c -= 13;
			else if (c >= 'N' && c <= 'Z') c -= 13;
			result.append(c);
		}
		return result.toString();
	}

	/**
	 * <pre>
	 * Encodes a string by shifting characters within the ASCII table.
	 * </pre>
	 *
	 * @author arthemus
	 * @param value
	 *            The value to encode.
	 * @return The encoded value.
	 */
	public static final String rot47(String value) {
		final String temp = notNull(value);
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < temp.length(); i++) {
			char character = temp.charAt(i);
			if (character != ' ') {
				character += 47;
				if (character > '~') character -= 94;
			}
			result.append(character);
		}
		return result.toString();
	}

}
