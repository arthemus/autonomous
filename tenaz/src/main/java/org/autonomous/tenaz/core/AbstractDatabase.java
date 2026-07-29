package org.autonomous.tenaz.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class to represent a specific DBMS.
 *
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public abstract class AbstractDatabase implements DatabaseConnection {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDatabase.class);

	private final String host;
	private final String database;
	private final int port;
	private final String username;
	private final String password;

	public AbstractDatabase(String host, String database, int port, String username,
			String password) {
		super();
		this.host = host;
		this.database = database;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	/**
	 * Returns the DBMS name.
	 *
	 * @return The DBMS name.
	 */
	public abstract String getName();

	/**
	 * Returns the connection URL for the database.
	 *
	 * @return The JDBC connection URL.
	 */
	public abstract String getUrl();

	/**
	 * Returns the driver identifier string for the database.
	 *
	 * @return The JDBC driver class name.
	 */
	public abstract String getDriver();

	/**
	 * Returns the database file/schema name.
	 *
	 * @return The database name.
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * Returns the address of the database hosting server.
	 *
	 * @return The host address.
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Communication port with the database.
	 *
	 * @return The port number.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Connection username.
	 *
	 * @return The username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Connection password.
	 *
	 * @return The password.
	 */
	public String getPassword() {
		return password;
	}

	@Override
	public Connection getConnection() throws ConnectionException {
		try {
			Class.forName(getDriver());
			Connection con = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
			return con;
		} catch (ClassNotFoundException error) {
			throw new ConnectionException("The connection driver could not be found.\nError: " + error.getMessage());
		} catch (SQLException error) {
			throw new ConnectionException("Problems with the connection string.\nError: " + error.getMessage());
		}
	}

	/**
	 * Obtains a connection from a JNDI context.
	 *
	 * @param nameContext
	 *            The JNDI resource name.
	 * @return A live JDBC connection.
	 * @throws SQLException
	 *             If the connection cannot be established.
	 */
	public Connection getConnectionByContext(String nameContext) throws SQLException {
		try {
			Context context = (Context) new InitialContext().lookup("java:/comp/env");
			DataSource dataSource = (DataSource) context.lookup("jdbc/".concat(nameContext));
			return dataSource.getConnection();
		} catch (NamingException e) {
			throw new SQLException("The connection context seems to be incorrect or was not found.\nError: " + e.getMessage(), e);
		} catch (SQLException e) {
			throw new SQLException("Problems obtaining the connection from the context.\nError: " + e.getMessage(), e);
		}
	}

	@Override
	public String toString() {
		// The password is intentionally omitted to avoid leaking credentials.
		LOGGER.debug("Building toString for {}", getName());
		return getName() + " [host=" + host + ", database=" + database + ", port="
				+ port + ", username=" + username + "]";
	}
}
