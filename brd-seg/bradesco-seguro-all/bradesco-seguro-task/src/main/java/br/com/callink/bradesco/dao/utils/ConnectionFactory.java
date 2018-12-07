package br.com.callink.bradesco.dao.utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author michael
 *
 */
public class ConnectionFactory {

	/**
	 * Cria nova conexão com banco de dados a partir dos dados informados.
	 * 
	 * @param driver
	 * @param jdbcUrl
	 * @param usuario
	 * @param senha
	 * @return
	 * @throws SQLException 
	 */
	public static Connection create(String driver, String jdbcUrl, String usuario, String senha) throws SQLException {
		
		try {
			DriverManager.registerDriver((Driver) Class.forName(driver).newInstance());
			return DriverManager.getConnection(jdbcUrl, usuario, senha);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
