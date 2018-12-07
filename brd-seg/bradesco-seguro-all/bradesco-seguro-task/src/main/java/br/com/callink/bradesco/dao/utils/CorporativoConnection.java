package br.com.callink.bradesco.dao.utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import br.com.callink.bradesco.dao.ICorporativoDAO;
import br.com.callink.bradesco.dao.impl.CorporativoDAO;
import br.com.callink.bradesco.seguro.commons.utils.ConnectionUtils;
import br.com.callink.bradesco.seguro.commons.utils.PropertiesUtils;
import br.com.callink.bradesco.seguro.commons.utils.StringUtils;
import br.com.callink.bradesco.task.sincronizadores.impl.SincronizadorCargo;

public class CorporativoConnection {
	
	private static final Logger logger = Logger.getLogger(SincronizadorCargo.class);
	private static Connection conexaoDbCooporativo;
	private static ICorporativoDAO corporativoDAO;

	public static ICorporativoDAO getConnection() throws SQLException {
		if (conexaoDbCooporativo != null && !conexaoDbCooporativo.isClosed()) {
			return corporativoDAO;
		}
		
		PropertiesUtils.load("application.properties");
		
		// corporativo
		String dbCorporativoJdbcUrl = PropertiesUtils.getProperty("db_corporativo_jdbc_url");
		String dbCorporativoJdbcDriver = PropertiesUtils.getProperty("db_corporativo_jdbc_driver");
		String dbCorporativoUsuario = PropertiesUtils.getProperty("db_corporativo_usuario");
		String dbCorporativoSenha = PropertiesUtils.getProperty("db_corporativo_senha");
		String dbCorporativoOwner = PropertiesUtils.getProperty("db_corporativo_owner");

		/*
		 * efetua validacao de dados de conexao - Corporativo
		 */
		if (StringUtils.isEmpty(dbCorporativoJdbcUrl)) {
			throw new IllegalStateException("Url JDBC de Conexão do [Corporativo] não foi informada.");
		}

		if (StringUtils.isEmpty(dbCorporativoJdbcDriver)) {
			throw new IllegalStateException("Driver JDBC de Conexão do [Corporativo] não foi informada.");
		}

		if (StringUtils.isEmpty(dbCorporativoUsuario)) {
			throw new IllegalStateException("Usuário de Conexão do [Corporativo] não foi informada.");
		}

		if (StringUtils.isEmpty(dbCorporativoSenha)) {
			throw new IllegalStateException("Senha de Conexão do [Corporativo] não foi informada.");
		}

		conexaoDbCooporativo = null;

		conexaoDbCooporativo = ConnectionFactory.create(dbCorporativoJdbcDriver, dbCorporativoJdbcUrl, dbCorporativoUsuario, dbCorporativoSenha);
		logger.info("\n --- Dados de Conexão: Corporativo --- \n " + ConnectionUtils.getMetadata(conexaoDbCooporativo));
		
		corporativoDAO = new CorporativoDAO(conexaoDbCooporativo, dbCorporativoOwner);
		
		return corporativoDAO;
	}
	
	public static void closeConnection() {
		ConnectionUtils.closeIgnore(conexaoDbCooporativo);
	}
}
