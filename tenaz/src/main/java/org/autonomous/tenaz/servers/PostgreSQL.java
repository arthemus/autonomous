package org.autonomous.tenaz.servers;

import org.autonomous.tenaz.core.AbstractDatabase;

/**
 * Class to connect to a PostgreSQL database.
 *
 * @author Arthemus C. Moreira
 * @since 13/05/2014
 *
 */
public final class PostgreSQL extends AbstractDatabase {

	public static final String DRIVER = "org.postgresql.Driver";

	public PostgreSQL(String host, String database, int port, String username,
			String password) {
		super(host, database, port, username, password);
	}

	@Override
	public String getName() {
		return "PostgreSQL";
	}

	@Override
	public String getUrl() {
		return "jdbc:postgresql://" + getHost() + ":" + getPort() + "/" + getDatabase();
	}

	@Override
	public String getDriver() {
		return DRIVER;
	}
}
