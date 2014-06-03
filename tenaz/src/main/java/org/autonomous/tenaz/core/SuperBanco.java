package org.autonomous.tenaz.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Classe abstrata para representar um expecifico SGDB.
 * 
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public abstract class SuperBanco implements Conexao {

	private final String host;
	private final String banco;
	private final int porta;
	private final String usuario;
	private final String senha;

	public SuperBanco(String host, String banco, int porta, String usuario,
			String senha) {
		super();
		this.host = host;
		this.banco = banco;
		this.porta = porta;
		this.usuario = usuario;
		this.senha = senha;
	}

	/**
	 * Retorna o nome do SGDB.
	 * 
	 * @return
	 */
	abstract public String getNome();

	/**
	 * Retorna a URL de conexão com o banco.
	 * 
	 * @return
	 */
	abstract public String getUrl();
	
	/**
	 * Retorna a String de identificação do Driver do banco.
	 * 
	 * @return
	 */
	abstract public String getDrive();

	/**
	 * Retorna o nome do arquivo do banco de dados.
	 * 
	 * @return
	 */
	public String getBanco() {
		return banco;
	}

	/**
	 * Retorna o endereço do servidor de hospedagem do banco.
	 * 
	 * @return
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Porta de comunicação com o banco.
	 * 
	 * @return
	 */
	public int getPorta() {
		return porta;
	}

	/**
	 * Usuário de conexão.
	 * 
	 * @return
	 */
	public String getUsername() {
		return usuario;
	}

	/**
	 * Senha para conexão.
	 * 
	 * @return
	 */
	public String getPassword() {
		return senha;
	}

	@Override
	public Connection getConexao() throws ConexaoException {
		try {
			Class.forName(getDrive());			
			Connection con = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
			return con;
		} catch (ClassNotFoundException erro) {
			throw new ConexaoException("O driver de conexao não pode ser encontrado.\nErro: " + erro.getMessage());
		} catch (SQLException erro) {
			throw new ConexaoException("Problemas com a String de conexao.\nErro: " + erro.getMessage());
		}
	}
	
	public Connection getConexaoContext(String nameContext) throws SQLException {
		try {
			Context context = (Context) new InitialContext().lookup("java:/comp/env");
			DataSource dataSource = (DataSource) context.lookup("jdbc/".concat(nameContext));
			return dataSource.getConnection();
		} catch (NamingException e) {
			throw new SQLException("O contexto de conexão parece estar incorreto ou não foi encontrado.\nErro: " + e.getMessage());
		} catch (SQLException e) {
			throw new SQLException("Problemas para obter a conexão a partir do contexto.\nErro: " + e.getMessage());
		}
	}
	
	@Override
	public String toString() {
		return getNome() + " [host=" + host + ", banco=" + banco + ", porta="
				+ porta + ", usuario=" + usuario + ", senha=" + senha + "]";
	}
}
