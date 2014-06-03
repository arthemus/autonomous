package org.autonomous.tenaz.core;

import java.sql.Connection;

public interface Conexao {

	Connection getConexao() throws ConexaoException;
}
