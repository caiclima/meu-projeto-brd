package br.com.callink.bradesco.seguro.commons.utils;

import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

/**
 * Classe utilitária para conexões jdbc
 * 
 * @author michael
 * 
 */
public class ConnectionUtils {

	/**
	 * Fecha conexão ignorando qualquer erro que ocasionalmente ocorra.
	 * 
	 * @param conn
	 */
	public static void closeIgnore(Connection conn) {
		try {
			if(conn != null) conn.close();
		} catch (SQLException e) {
			// ignore
		}
	}
	
	public static void closeIgnore(ResultSet rs) {
		try {
			if(rs != null) rs.close();
		} catch (SQLException e) {
			// ignore
		}
	}
	
	public static void closeIgnore(Statement st) {
		try {
			if(st != null) st.close();
		} catch (SQLException e) {
			// ignore
		}
	}

	/**
	 * Executa um SQL nativo
	 * 
	 * @param conn
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet execute(Connection conn, String sql) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet query = ps.executeQuery();
		return query;
	}
	
	public static CallableStatement executeProcedure(Connection conn, String proc) throws SQLException {
		CallableStatement cs = conn.prepareCall(proc);
		return cs;
	}
	
	public static int executeUpdate(Connection conn, String sql) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(sql);
		int total = ps.executeUpdate();
		closeIgnore(ps);
		return total;
	}
	
	public static <T> T executeUpdateGenerateKey(Connection conn, String sql) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		int total = ps.executeUpdate();
		if(total > 0){
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if(generatedKeys.next()){
				return (T) generatedKeys.getObject(1);
			}
		}
		
		closeIgnore(ps);
		return null;
	}
	
	/**
	 * Imprime informações da conexão para o {@link OutputStream} informado.
	 * 
	 * @param conn
	 * @param out
	 * @throws SQLException
	 */
	public static void printMetadata(Connection conn, OutputStream out) throws SQLException {
		PrintStream printer = new PrintStream(out);
		printer.println(getMetadata(conn).toString());
	}

	/**
	 * Retorna metadados da conexão como {@link String}
	 * 
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	public static String getMetadata(Connection conn) throws SQLException {
		DatabaseMetaData m = conn.getMetaData();
		String name = m.getDatabaseProductName();
		String version = m.getDatabaseProductVersion();
		String url = m.getURL();
		String username = m.getUserName();
		String driver = m.getDriverName();
		String driverVersion = m.getDriverVersion();

		StringBuilder output = new StringBuilder();
		output.append(MessageFormat.format("Banco de Dados: {0} \n", name));
		output.append(MessageFormat.format("Versão: {0} \n", version));
		output.append(MessageFormat.format("URL: {0} \n", url));
		output.append(MessageFormat.format("Usuario: {0} \n", username));
		output.append(MessageFormat.format("Driver: {0} \n", driver));
		output.append(MessageFormat.format("Versão Driver: {0} \n", driverVersion));

		return output.toString();
	}
}
