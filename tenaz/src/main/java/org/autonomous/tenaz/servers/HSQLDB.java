package org.autonomous.tenaz.servers;

import org.autonomous.tenaz.core.AbstractDatabase;

/**
 * Representation of the HSQLDB database.
 *
 * <a>http://hsqldb.org/</a>
 *
 * Driver: <b>org.hsqldb.jdbcDriver</b>
 *
 * Example URL for a file-based database connection:
 * <b>jdbc:hsqldb:file:/opt/db/testdb</b>
 *
 * @author arthemus
 * @since 22/04/2014
 */
public class HSQLDB extends AbstractDatabase {

	public HSQLDB(String host, String database, int port, String username,
			String password) {
		super(host, database, port, username, password);

	}

	@Override
	public String getName() {
		return "HSQLDB";
	}

	@Override
	public String getUrl() {
		return "jdbc:hsqldb:" + getHost() + ":" + getDatabase();
	}

	@Override
	public String getDriver() {
		return "org.hsqldb.jdbcDriver";
	}

}
