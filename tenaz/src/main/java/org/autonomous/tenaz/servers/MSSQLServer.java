package org.autonomous.tenaz.servers;

import org.autonomous.tenaz.core.AbstractDatabase;

/**
 * Class to manage connections with a Microsoft SQL Server database.
 *
 * To use this class the manufacturer's JDBC driver must be obtained.
 *
 * Note: <i>To connect to SQL Server 2005, consider adding the
 * <b>sqljdbc4.jar</b> file to the project classpath</i>
 *
 * Driver: <b>com.microsoft.sqlserver.jdbc.SQLServerDriver</b>
 *
 * URL: <b>jdbc:sqlserver://[host];[port]:DatabaseName=[database]</b>
 *
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public final class MSSQLServer extends AbstractDatabase {

	public static final String DRIVER = "net.sourceforge.jtds.jdbc.Driver";

	public MSSQLServer(String host, String database, int port, String username,
			String password) {
		super(host, database, port, username, password);
	}

	@Override
	public String getName() {
		return "Microsoft SQL Server";
	}

	@Override
	public String getUrl() {
		return "jdbc:jtds:sqlserver://" + getHost() + ":" + getPort() + "/"
				+ getDatabase();
	}

	@Override
	public String getDriver() {
		return DRIVER;
	}

}
