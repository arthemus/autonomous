package org.autonomous.tenaz.servidores;

import org.autonomous.tenaz.core.SuperBanco;

/**
 * Classe para gerenciar conexões com um banco de dados Microsoft SQL Server.
 * 
 * Para utilização dessa classe deve-se obter o Driver JDBC do fabricante.
 * 
 * Obs: <i>Para conexão com o SQL Server 2005, procure adicionar ao classpath do
 * projeto o arquivo <b>sqljdbc4.jar</b></i>
 * 
 * Driver: <b>com.microsoft.sqlserver.jdbc.SQLServerDriver</b>
 * 
 * URL: <b>jdbc:sqlserver://[host];[porta]:DatabaseName=[banco]</b>
 * 
 * @author Arthemus C. Moreira
 * @since 1.0.0
 */
public final class MSSQLServer2005 extends SuperBanco {

	public static final String DRIVER = "net.sourceforge.jtds.jdbc.Driver";

	public MSSQLServer2005(String host, String banco, int porta, String usuario,
			String senha) {
		super(host, banco, porta, usuario, senha);
	}

	@Override
	public String getNome() {
		return "Microsoft SQL Server";
	}

	@Override
	public String getUrl() {
		return "jdbc:jtds:sqlserver://" + getHost() + ":" + getPorta() + "/"
				+ getBanco();
	}

	@Override
	public String getDrive() {
		return DRIVER;
	}

}
