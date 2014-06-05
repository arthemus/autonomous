package org.autonomous.tenaz.servidores;

import org.autonomous.tenaz.core.SuperBanco;

/**
 * Definições para conexão com um banco de dados Oracle.
 * 
 * @author arthemus.moreira
 * @since 05/06/2014
 * @see SuperBanco
 *
 */
public class Oracle extends SuperBanco {

	public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	
	public Oracle(String host, String banco, int porta, String usuario,
			String senha) {
		super(host, banco, porta, usuario, senha);
	}

	@Override
	public String getNome() {
		return "Oracle";
	}

	@Override
	public String getUrl() {
		String url = "jdbc:oracle:thin:@" + super.getHost() + ":" + super.getPorta() + ":" + super.getBanco();		
		return url;
	}

	@Override
	public String getDrive() {
		return DRIVER;
	}

}
