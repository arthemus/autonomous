package org.autonomous.functions.security;

/**
 * Exceção criada especificamente para o Crypto.
 * 
 * @author Walter Portugal
 *
 */

public class CryptoException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public CryptoException(Exception e){
		super(e);
	}

}
