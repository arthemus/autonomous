package org.autonomous.tenaz.servidores;

import org.autonomous.tenaz.core.SuperBanco;

/**
 * Classe para gerenciar conexões com um banco de dados Firebird.
 * 
 * Para utilizar esta classe é necessário baixar o driver JDBC do fabricante:
 * 
 * <a>http://www.firebirdsql.org/en/jdbc-driver/</a>
 * 
 * Driver: <b>org.firebirdsql.jdbc.FBDriver</b>
 * 
 * URL: <b>jdbc:firebirdsql:[host]/[porta]:[caminho até o arquivo .fdb]</b>
 * 
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public final class Firebird extends SuperBanco {

	public static final String DRIVER = "org.firebirdsql.jdbc.FBDriver";

	public Firebird(String host, String banco, int porta, String usuario,
			String senha) {
		super(host, banco, porta, usuario, senha);
	}

	@Override
	public String getNome() {
		return "Firebird";
	}

	@Override
	public String getUrl() {
		return "jdbc:firebirdsql:" + getHost() + "/" + getPorta() + ":"
				+ getBanco();
	}

	@Override
	public String getDrive() {
		return DRIVER;
	}

}