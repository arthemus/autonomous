package org.autonomous.tenaz.commons;

import static org.assertj.core.api.Assertions.assertThat;

import org.autonomous.tenaz.servers.Firebird;
import org.autonomous.tenaz.servers.HSQLDB;
import org.autonomous.tenaz.servers.MSSQLServer;
import org.autonomous.tenaz.servers.MySQL;
import org.autonomous.tenaz.servers.Oracle;
import org.autonomous.tenaz.servers.PostgreSQL;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link DatabaseDateFormat}.
 */
class DatabaseDateFormatTest {

	@Test
	@DisplayName("should return yyyy-MM-dd when database is Firebird")
	void shouldReturnFirebirdFormatWhenDatabaseIsFirebird() {
		// Arrange
		Firebird firebird = new Firebird("host", "db", 3050, "user", "pwd");

		// Act
		String format = DatabaseDateFormat.getFormat(firebird);

		// Assert
		assertThat(format).isEqualTo("yyyy-MM-dd");
	}

	@Test
	@DisplayName("should return yyyyMMdd when database is MSSQLServer")
	void shouldReturnMssqlFormatWhenDatabaseIsMssqlServer() {
		// Arrange
		MSSQLServer mssql = new MSSQLServer("host", "db", 1433, "user", "pwd");

		// Act
		String format = DatabaseDateFormat.getFormat(mssql);

		// Assert
		assertThat(format).isEqualTo("yyyyMMdd");
	}

	@Test
	@DisplayName("should return empty string when database is MySQL")
	void shouldReturnEmptyWhenDatabaseIsMysql() {
		// Arrange
		MySQL mysql = new MySQL("host", "db", 3306, "user", "pwd");

		// Act
		String format = DatabaseDateFormat.getFormat(mysql);

		// Assert
		assertThat(format).isEmpty();
	}

	@Test
	@DisplayName("should return empty string when database is PostgreSQL")
	void shouldReturnEmptyWhenDatabaseIsPostgreSql() {
		// Arrange
		PostgreSQL postgres = new PostgreSQL("host", "db", 5432, "user", "pwd");

		// Act
		String format = DatabaseDateFormat.getFormat(postgres);

		// Assert
		assertThat(format).isEmpty();
	}

	@Test
	@DisplayName("should return empty string when database is HSQLDB")
	void shouldReturnEmptyWhenDatabaseIsHsqldb() {
		// Arrange
		HSQLDB hsqldb = new HSQLDB("host", "db", 9001, "user", "pwd");

		// Act
		String format = DatabaseDateFormat.getFormat(hsqldb);

		// Assert
		assertThat(format).isEmpty();
	}

	@Test
	@DisplayName("should return empty string when database is Oracle")
	void shouldReturnEmptyWhenDatabaseIsOracle() {
		// Arrange
		Oracle oracle = new Oracle("host", "db", 1521, "user", "pwd");

		// Act
		String format = DatabaseDateFormat.getFormat(oracle);

		// Assert
		assertThat(format).isEmpty();
	}
}
