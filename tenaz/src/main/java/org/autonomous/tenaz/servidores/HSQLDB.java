package org.autonomous.tenaz.servidores;

import org.autonomous.tenaz.core.SuperBanco;

/**
 * Representação do banco de dados HSQLDB.
 * 
 * <a>http://hsqldb.org/</a>
 * 
 * Driver: <b>org.hsqldb.jdbcDriver</b>
 * 
 * Exemplo de URL para conexão com banco do tipo arquivo:
 * <b>jdbc:hsqldb:file:/opt/db/testdb</b>
 * 
 * @author arthemus
 * @since 22/04/2014
 */
public class HSQLDB extends SuperBanco {

	public HSQLDB(String host, String banco, int porta, String usuario,
			String senha) {
		super(host, banco, porta, usuario, senha);

	}

	@Override
	public String getNome() {
		return "HSQLDB";
	}

	@Override
	public String getUrl() {
		return "jdbc:hsqldb:" + getHost() + ":" + getBanco();
	}

	@Override
	public String getDrive() {
		return "org.hsqldb.jdbcDriver";
	}

}
