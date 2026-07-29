package org.autonomous.tenaz.hibernate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.autonomous.tenaz.servers.Firebird;
import org.autonomous.tenaz.servers.HSQLDB;
import org.autonomous.tenaz.servers.MSSQLServer;
import org.autonomous.tenaz.servers.MySQL;
import org.autonomous.tenaz.servers.Oracle;
import org.autonomous.tenaz.servers.PostgreSQL;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link HibernateUtil}.
 */
class HibernateUtilTest {

	@Test
	@DisplayName("should return Firebird dialect when database is Firebird")
	void shouldReturnFirebirdDialectWhenDatabaseIsFirebird() {
		// Arrange
		Firebird firebird = new Firebird("host", "db", 3050, "user", "pwd");

		// Act
		String dialect = HibernateUtil.getDialect(firebird);

		// Assert
		assertThat(dialect).isEqualTo("org.hibernate.dialect.FirebirdDialect");
	}

	@Test
	@DisplayName("should return MySQL dialect when database is MySQL")
	void shouldReturnMysqlDialectWhenDatabaseIsMysql() {
		// Arrange
		MySQL mysql = new MySQL("host", "db", 3306, "user", "pwd");

		// Act
		String dialect = HibernateUtil.getDialect(mysql);

		// Assert
		assertThat(dialect).isEqualTo("org.hibernate.dialect.MySQLInnoDBDialect");
	}

	@Test
	@DisplayName("should return SQL Server dialect when database is MSSQLServer")
	void shouldReturnSqlServerDialectWhenDatabaseIsMssqlServer() {
		// Arrange
		MSSQLServer mssql = new MSSQLServer("host", "db", 1433, "user", "pwd");

		// Act
		String dialect = HibernateUtil.getDialect(mssql);

		// Assert
		assertThat(dialect).isEqualTo("org.hibernate.dialect.SQLServer2005Dialect");
	}

	@Test
	@DisplayName("should return HSQL dialect when database is HSQLDB")
	void shouldReturnHsqlDialectWhenDatabaseIsHsqldb() {
		// Arrange
		HSQLDB hsqldb = new HSQLDB("host", "db", 9001, "user", "pwd");

		// Act
		String dialect = HibernateUtil.getDialect(hsqldb);

		// Assert
		assertThat(dialect).isEqualTo("org.hibernate.dialect.HSQLDialect");
	}

	@Test
	@DisplayName("should return PostgreSQL dialect when database is PostgreSQL")
	void shouldReturnPostgreSqlDialectWhenDatabaseIsPostgreSql() {
		// Arrange
		PostgreSQL postgres = new PostgreSQL("host", "db", 5432, "user", "pwd");

		// Act
		String dialect = HibernateUtil.getDialect(postgres);

		// Assert
		assertThat(dialect).isEqualTo("org.hibernate.dialect.PostgreSQLDialect");
	}

	@Test
	@DisplayName("should throw RuntimeException when database dialect is not defined")
	void shouldThrowRuntimeExceptionWhenDatabaseDialectIsNotDefined() {
		// Arrange
		Oracle oracle = new Oracle("host", "db", 1521, "user", "pwd");

		// Act
		// Assert
		assertThatThrownBy(() -> HibernateUtil.getDialect(oracle))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("Database not defined");
	}
}
