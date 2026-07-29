package org.autonomous.tenaz.servers;

import org.autonomous.tenaz.core.AbstractDatabase;

/**
 * Class to manage connections with a Firebird database.
 *
 * To use this class it is necessary to download the manufacturer's JDBC driver:
 *
 * <a>http://www.firebirdsql.org/en/jdbc-driver/</a>
 *
 * Driver: <b>org.firebirdsql.jdbc.FBDriver</b>
 *
 * URL: <b>jdbc:firebirdsql:[host]/[port]:[path to the .fdb file]</b>
 *
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public final class Firebird extends AbstractDatabase {

	public static final String DRIVER = "org.firebirdsql.jdbc.FBDriver";

	public Firebird(String host, String database, int port, String username,
			String password) {
		super(host, database, port, username, password);
	}

	@Override
	public String getName() {
		return "Firebird";
	}

	@Override
	public String getUrl() {
		return "jdbc:firebirdsql:" + getHost() + "/" + getPort() + ":"
				+ getDatabase();
	}

	@Override
	public String getDriver() {
		return DRIVER;
	}

}
