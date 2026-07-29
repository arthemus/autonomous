package org.autonomous.tenaz.servers;

import org.autonomous.tenaz.core.AbstractDatabase;

/**
 * Class to connect to a MySQL database.
 *
 * The manufacturer's driver must be added to the project classpath.
 *
 * At the time this class was created, the driver could be found at:
 *
 * <a>http://dev.mysql.com/downloads/connector/j/</a>
 *
 * Driver: <b>com.mysql.jdbc.Driver</b>
 *
 * URL: <b>jdbc:mysql://[host]:[port]/[database]</b>
 *
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public final class MySQL extends AbstractDatabase {

	public static final String DRIVER = "com.mysql.jdbc.Driver";

	public MySQL(String host, String database, int port, String username,
			String password) {
		super(host, database, port, username, password);
	}

	@Override
	public String getName() {
		return "MySQL";
	}

	@Override
	public String getUrl() {
		return "jdbc:mysql://" + getHost() + ":" + getPort() + "/"
				+ getDatabase();
	}

	@Override
	public String getDriver() {
		return DRIVER;
	}
}
