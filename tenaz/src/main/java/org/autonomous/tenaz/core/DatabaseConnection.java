package org.autonomous.tenaz.core;

import java.sql.Connection;

/**
 * Contract for obtaining a JDBC connection to a database.
 *
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public interface DatabaseConnection {

	/**
	 * Obtains an open JDBC connection.
	 *
	 * @return A live JDBC connection.
	 * @throws ConnectionException
	 *             If the connection cannot be established.
	 */
	Connection getConnection() throws ConnectionException;
}
