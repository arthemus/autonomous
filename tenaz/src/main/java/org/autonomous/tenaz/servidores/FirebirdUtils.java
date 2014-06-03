package org.autonomous.tenaz.servidores;

import java.util.LinkedList;
import java.util.List;

import org.autonomous.tenaz.core.PersistException;
import org.autonomous.tenaz.hibernate.SQLHibernateSearch;

/**
 * 
 * Classe utilitários para o banco Firebird.
 * 
 * @author arthemus
 * @since 06/05/2014
 */
public class FirebirdUtils {

	private final SQLHibernateSearch _sqlSearch;

	public FirebirdUtils(SQLHibernateSearch _sqlSearch) {
		this._sqlSearch = _sqlSearch;
	}

	/**
	 * Retorna o nome de todas as tabelas do banco de dados em ordem alfabética.
	 * 
	 * @return
	 */
	public List<String> getTabelas() {
		String query = "SELECT RDB$RELATION_NAME " 
				+ "FROM RDB$RELATIONS "
				+ "WHERE (1=1) " 
				+ "AND (RDB$VIEW_BLR IS NULL) "
				+ "AND (RDB$SYSTEM_FLAG = 0 OR RDB$SYSTEM_FLAG IS NULL) "
				+ "ORDER BY RDB$RELATION_NAME";
		LinkedList<String> tabelas = new LinkedList<String>();
		try {
			List<Object> result = _sqlSearch.getList(query, null);
			if (!result.isEmpty())
				for (Object item : result)
					tabelas.add(((String) item).trim());
		} catch (PersistException e) {
			e.printStackTrace();
		}
		return tabelas;
	}
	
	/**
	 * Obtem uma lista das tabelas de dependencia da respectiva tabela 
	 * informada como paramêtro.
	 * 
	 * @return
	 */
	public List<String> getTabelasRelacionadas(String tabelaReferente) {
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
				+ "AND (i.RDB$RELATION_NAME = '" + tabelaReferente + "') "
				+ "GROUP BY i2.RDB$RELATION_NAME";
		LinkedList<String> tabelas = new LinkedList<String>();
		try {
			List<Object> result = _sqlSearch.getList(query, null);
			if (!result.isEmpty())
				for (Object item : result)
					tabelas.add(((String) item).trim());
		} catch (PersistException e) {
			e.printStackTrace();
		}
		return tabelas;
	}

	/**
	 * Obtem uma lista com os nomes das constraints Foreign Keys de uma tabela.
	 * 
	 * @param tabelaReferente
	 * @return
	 */
	public List<String> getConstraintsFK(String tabelaReferente) {
		String query = "select rc.RDB$CONSTRAINT_NAME "
				+ "from RDB$RELATION_CONSTRAINTS rc "
				+ "where (1=1) "
				+ "and (rc.RDB$CONSTRAINT_TYPE = 'FOREIGN KEY') "
				+ "and (rc.RDB$RELATION_NAME = '" + tabelaReferente + "') ";				
		LinkedList<String> tabelas = new LinkedList<String>();
		try {
			List<Object> result = _sqlSearch.getList(query, null);
			if (!result.isEmpty())
				for (Object item : result)
					tabelas.add(((String) item).trim());
		} catch (PersistException e) {
			e.printStackTrace();
		}
		return tabelas;
	}
}
