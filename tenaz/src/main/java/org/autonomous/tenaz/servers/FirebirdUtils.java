package org.autonomous.tenaz.servers;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.autonomous.tenaz.core.PersistException;
import org.autonomous.tenaz.hibernate.SQLHibernateSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for the Firebird database.
 *
 * @author arthemus
 * @since 06/05/2014
 */
public class FirebirdUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(FirebirdUtils.class);

	private final SQLHibernateSearch sqlSearch;

	public FirebirdUtils(SQLHibernateSearch sqlSearch) {
		this.sqlSearch = sqlSearch;
	}

	/**
	 * Returns the names of all database tables in alphabetical order.
	 *
	 * @return The list of table names.
	 */
	public List<String> getTables() {
		String query = "SELECT RDB$RELATION_NAME "
				+ "FROM RDB$RELATIONS "
				+ "WHERE (1=1) "
				+ "AND (RDB$VIEW_BLR IS NULL) "
				+ "AND (RDB$SYSTEM_FLAG = 0 OR RDB$SYSTEM_FLAG IS NULL) "
				+ "ORDER BY RDB$RELATION_NAME";
		LinkedList<String> tables = new LinkedList<String>();
		try {
			List<Object> result = sqlSearch.getList(query, null);
			if (!result.isEmpty()) {
				for (Object item : result) {
					tables.add(((String) item).trim());
				}
			}
		} catch (PersistException e) {
			LOGGER.error("Could not list the Firebird tables", e);
		}
		return tables;
	}

	/**
	 * Obtains a list of the dependency tables of the table informed as a
	 * parameter. The table name is bound as a named parameter to avoid SQL
	 * injection.
	 *
	 * @param referenceTable
	 *            The table whose related tables should be retrieved.
	 * @return The list of related table names.
	 */
	public List<String> getRelatedTables(String referenceTable) {
		String query = "SELECT i2.RDB$RELATION_NAME AS FK_TABLE "
				+ "FROM RDB$INDEX_SEGMENTS s "
				+ "LEFT JOIN RDB$INDICES i ON i.RDB$INDEX_NAME = s.RDB$INDEX_NAME "
				+ "LEFT JOIN RDB$RELATION_CONSTRAINTS rc ON rc.RDB$INDEX_NAME = s.RDB$INDEX_NAME "
				+ "LEFT JOIN RDB$REF_CONSTRAINTS refc ON rc.RDB$CONSTRAINT_NAME = refc.RDB$CONSTRAINT_NAME "
				+ "LEFT JOIN RDB$RELATION_CONSTRAINTS rc2 ON rc2.RDB$CONSTRAINT_NAME = refc.RDB$CONST_NAME_UQ "
				+ "LEFT JOIN RDB$INDICES i2 ON i2.RDB$INDEX_NAME = rc2.RDB$INDEX_NAME "
				+ "LEFT JOIN RDB$INDEX_SEGMENTS s2 ON i2.RDB$INDEX_NAME = s2.RDB$INDEX_NAME AND s.RDB$FIELD_POSITION = s2.RDB$FIELD_POSITION "
				+ "WHERE (1=1) "
				+ "AND (i.RDB$SYSTEM_FLAG = 0 OR i.RDB$SYSTEM_FLAG IS NULL) "
				+ "AND (rc.RDB$CONSTRAINT_TYPE = 'FOREIGN KEY') "
				+ "AND (i.RDB$RELATION_NAME = :referenceTable) "
				+ "GROUP BY i2.RDB$RELATION_NAME";
		Map<String, Object> params = Collections.<String, Object>singletonMap("referenceTable", referenceTable);
		LinkedList<String> tables = new LinkedList<String>();
		try {
			List<Object> result = sqlSearch.getList(query, params);
			if (!result.isEmpty()) {
				for (Object item : result) {
					tables.add(((String) item).trim());
				}
			}
		} catch (PersistException e) {
			LOGGER.error("Could not list the related tables for {}", referenceTable, e);
		}
		return tables;
	}

	/**
	 * Obtains a list with the names of the foreign key constraints of a table.
	 * The table name is bound as a named parameter to avoid SQL injection.
	 *
	 * @param referenceTable
	 *            The table whose foreign key constraints should be retrieved.
	 * @return The list of foreign key constraint names.
	 */
	public List<String> getForeignKeyConstraints(String referenceTable) {
		String query = "select rc.RDB$CONSTRAINT_NAME "
				+ "from RDB$RELATION_CONSTRAINTS rc "
				+ "where (1=1) "
				+ "and (rc.RDB$CONSTRAINT_TYPE = 'FOREIGN KEY') "
				+ "and (rc.RDB$RELATION_NAME = :referenceTable) ";
		Map<String, Object> params = Collections.<String, Object>singletonMap("referenceTable", referenceTable);
		LinkedList<String> tables = new LinkedList<String>();
		try {
			List<Object> result = sqlSearch.getList(query, params);
			if (!result.isEmpty()) {
				for (Object item : result) {
					tables.add(((String) item).trim());
				}
			}
		} catch (PersistException e) {
			LOGGER.error("Could not list the foreign key constraints for {}", referenceTable, e);
		}
		return tables;
	}
}
