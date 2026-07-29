package org.autonomous.tenaz.hibernate;

import org.autonomous.tenaz.core.AbstractDatabase;
import org.autonomous.tenaz.servers.Firebird;
import org.autonomous.tenaz.servers.HSQLDB;
import org.autonomous.tenaz.servers.MSSQLServer;
import org.autonomous.tenaz.servers.MySQL;
import org.autonomous.tenaz.servers.PostgreSQL;

/**
 * Utility class for the Hibernate framework.
 *
 * @author arthemus
 * @since 22/04/2014
 */
public class HibernateUtil {

	/**
	 * Returns the correct dialect according to the database being used.
	 *
	 * The described dialects correspond to Hibernate version 3.6.10.
	 *
	 * @param database
	 *            The database descriptor.
	 * @return The Hibernate dialect class name, or {@code null} when no dialect
	 *         matches the database.
	 */
	public static String getDialect(AbstractDatabase database) {
		String hibernateDialect = null;
		if (database instanceof Firebird) {
			hibernateDialect = "org.hibernate.dialect.FirebirdDialect";
		} else if (database instanceof MySQL) {
			hibernateDialect = "org.hibernate.dialect.MySQLInnoDBDialect";
		} else if (database instanceof MSSQLServer) {
			hibernateDialect = "org.hibernate.dialect.SQLServer2005Dialect";
		} else if (database instanceof HSQLDB) {
			hibernateDialect = "org.hibernate.dialect.HSQLDialect";
		} else if (database instanceof PostgreSQL) {
			hibernateDialect = "org.hibernate.dialect.PostgreSQLDialect";
		}
		if (hibernateDialect == null) {
			throw new RuntimeException("Database not defined to obtain its Hibernate dialect.");
		}
		return hibernateDialect;
	}
}
