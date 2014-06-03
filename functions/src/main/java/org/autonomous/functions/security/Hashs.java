package org.autonomous.functions.security;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * Classe responsável pelos geradores de hash e códigos de segurança utilizados
 * no sistema, desde a criptografia de senhas, nomes e paramêtros externos.
 * 
 * @author Arthemus C. Moreira
 * @since 22/03/2013
 */
public final class Hashs {

	/**
	 * Gerador de strings criptografadas no padrão MD5.
	 * 
	 * @author arthemus
	 * @param value
	 *            Valor a ser criptografado.
	 * @return String hash.
	 */
	private static final String doMD5Hash(final String value) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(notNull(value).getBytes(Charset.forName("UTF8")));
			byte[] resultByte = messageDigest.digest();
			return Hex.encodeHexString(resultByte);			
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Algoritimo MD5 não disponível", e);
		}
	}

	/**
	 * Gerador MD5 com complemento.
	 * 
	 * @author arthemus
	 * @param value
	 * @param salt
	 * @return
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
	 * Para impedir que valores Nulos sejá tratados pelos métodos de codificação.
	 * 
	 * @author arthemus
	 * @param value
	 * @return
	 */
	static final String notNull(String value) {
		return (value == null ? "" : value);
	}
	
	/**
	 * <pre>
	 * Codifica uma string alterando caracteres entre [A..Z], [a..z].
	 * </pre>
	 *  
	 * @author arthemus
	 * @param string
	 * @return Valor codificado.
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
	 * Codifica uma string alterando caracteres dentro da tabela ASCII.
	 * </pre>
	 *  
	 * @author arthemus
	 * @param string
	 * @return Valor codificado.
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
