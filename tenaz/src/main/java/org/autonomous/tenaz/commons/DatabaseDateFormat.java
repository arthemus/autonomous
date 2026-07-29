package org.autonomous.tenaz.commons;

import org.autonomous.tenaz.core.AbstractDatabase;
import org.autonomous.tenaz.servers.Firebird;
import org.autonomous.tenaz.servers.MSSQLServer;

/**
 * Identifies the format a date must have to be handled in a given DBMS.
 *
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public class DatabaseDateFormat {

	/**
	 * Returns the correct format defined for the database.
	 *
	 * @param database
	 *            The database descriptor.
	 * @return The format for the date.
	 */
	public static String getFormat(AbstractDatabase database) {
		if (database instanceof Firebird) {
			return "yyyy-MM-dd";
		} else if (database instanceof MSSQLServer) {
			return "yyyyMMdd";
		} else {
			return "";
		}
	}
}
