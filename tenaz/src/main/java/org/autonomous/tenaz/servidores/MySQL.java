package org.autonomous.tenaz.servidores;

import org.autonomous.tenaz.core.SuperBanco;

/**
 * Classe para conexão com um banco de dados MySQL.
 * 
 * Deve ser adicionado ao classpath do projeto o driver do fabricante.
 * 
 * Até a criação dessa classe, o driver pode ser encontrado no endereço:
 * 
 * <a>http://dev.mysql.com/downloads/connector/j/</a>
 * 
 * Driver: <b>com.mysql.jdbc.Driver</b>
 * 
 * URL: <b>jdbc:mysql://[host]:[porta]/[banco]</b>
 * 
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public final class MySQL extends SuperBanco {

	public static final String DRIVER = "com.mysql.jdbc.Driver";

	public MySQL(String host, String banco, int porta, String usuario,
			String senha) {
		super(host, banco, porta, usuario, senha);
	}

	@Override
	public String getNome() {
		return "MySQL";
	}

	@Override
	public String getUrl() {
		return "jdbc:mysql://" + getHost() + ":" + getPorta() + "/"
				+ getBanco();
	}

	@Override
	public String getDrive() {
		return DRIVER;
	}
}