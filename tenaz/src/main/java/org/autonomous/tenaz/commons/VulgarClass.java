package org.autonomous.tenaz.commons;

import java.io.Serializable;

/**
 * Operações mais que ordinárias e rotineiras que precisam ser feitas
 * constantemente entre Entidades do banco de dados e classes de dominio.
 * 
 * @author arthemus
 * @since 25/09/2013
 */
public interface VulgarClass extends Serializable {

	/**
	 * Verifica se a classe em questão é nova ou está sendo editada.
	 * 
	 * @return
	 */
	boolean isNewInstance();

}
