package org.autonomous.tenaz.servers;

import org.autonomous.tenaz.core.AbstractDatabase;

/**
 * Definitions to connect to an Oracle database.
 *
 * @author arthemus.moreira
 * @since 05/06/2014
 * @see AbstractDatabase
 *
 */
public class Oracle extends AbstractDatabase {

	public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	public Oracle(String host, String database, int port, String username,
			String password) {
		super(host, database, port, username, password);
	}

	@Override
	public String getName() {
		return "Oracle";
	}

	@Override
	public String getUrl() {
		String url = "jdbc:oracle:thin:@" + super.getHost() + ":" + super.getPort() + ":" + super.getDatabase();
		return url;
	}

	@Override
	public String getDriver() {
		return DRIVER;
	}

}
