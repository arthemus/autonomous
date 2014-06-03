package org.autonomous.tenaz.hibernate;

import org.autonomous.tenaz.core.SuperBanco;
import org.autonomous.tenaz.servidores.Firebird;
import org.autonomous.tenaz.servidores.HSQLDB;
import org.autonomous.tenaz.servidores.MSSQLServer2005;
import org.autonomous.tenaz.servidores.MySQL;
import org.autonomous.tenaz.servidores.Postgre;

/**
 * Classe utilitária para o framework Hibernate.
 * 
 * @author arthemus
 * @since 22/04/2014
 */
public class HibernateUtil {

	/**
	 * Retorna o dialéto correto de acordo com o banco de dados utilizado.
	 * 
	 * Os dialétos descritos são de acordo com a versão 3.6.10 do Hibernate.
	 * 
	 * @param banco
	 * @return
	 */
	public static String getDialect(SuperBanco banco) {
		String hibernateDialect = null;
		if (banco instanceof Firebird) {
			hibernateDialect = "org.hibernate.dialect.FirebirdDialect";
		} else if (banco instanceof MySQL) {
			hibernateDialect = "org.hibernate.dialect.MySQLInnoDBDialect";
		} else if (banco instanceof MSSQLServer2005) {
			hibernateDialect = "org.hibernate.dialect.SQLServer2005Dialect";
		} else if (banco instanceof HSQLDB) {
			hibernateDialect = "org.hibernate.dialect.HSQLDialect";
		} else if (banco instanceof Postgre) {
			hibernateDialect = "org.hibernate.dialect.PostgreSQLDialect";
		}
		if (hibernateDialect == null)
			new RuntimeException("Banco de dados não definido para obter seu dialéto pelo Hibernate.");
		return hibernateDialect;
	}
}
