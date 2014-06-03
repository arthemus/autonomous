package org.autonomous.tenaz.commons;

import org.autonomous.tenaz.core.SuperBanco;
import org.autonomous.tenaz.servidores.Firebird;
import org.autonomous.tenaz.servidores.MSSQLServer2005;

/**
 * Identifica o formato que uma data deve ter para ser trabalhada em um
 * determinado sgbd.
 * 
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public class FormatoData {

	/**
	 * Retorna o formato correto definido para o banco.
	 * 
	 * @param banco
	 * @return Formato para a data.
	 */
	public static String getFormato(SuperBanco banco) {
		if (banco instanceof Firebird) return "yyyy-MM-dd";
		else if (banco instanceof MSSQLServer2005) return "yyyyMMdd";
		else return "";
	}
}
