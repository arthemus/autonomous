package org.autonomous.tenaz.servidores;

import org.autonomous.tenaz.core.SuperBanco;

/**
 * Classe para conexão com um banco de dados Postgre.
 * 
 * @author Arthemus C. Moreira
 * @since 13/05/2014
 * 
 */
public final class Postgre extends SuperBanco {

	public static final String DRIVER = "org.postgresql.Driver";

	public Postgre(String host, String banco, int porta, String usuario,
			String senha) {
		super(host, banco, porta, usuario, senha);
	}

	@Override
	public String getNome() {
		return "PostgreSQL";
	}

	@Override
	public String getUrl() {
		return "jdbc:postgresql://" + getHost() + ":" + getPorta() + "/" + getBanco();
	}

	@Override
	public String getDrive() {
		return DRIVER;
	}
}