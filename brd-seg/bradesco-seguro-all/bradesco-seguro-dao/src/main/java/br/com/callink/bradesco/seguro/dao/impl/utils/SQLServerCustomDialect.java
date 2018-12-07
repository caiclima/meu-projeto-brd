package br.com.callink.bradesco.seguro.dao.impl.utils;

import org.hibernate.dialect.SQLServer2008Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;

/**
 * 
 * Registra a function TOP (SQL SERVER), cujo uso segue:
 * 
 * TOP('NUMERO DE LINHAS','COLUNA').
 * EXEMPLO: select TOP('1','nome') from Usuario
 * 
 * @author michael
 *
 */
public class SQLServerCustomDialect extends SQLServer2008Dialect {
	
	public SQLServerCustomDialect(){
		 registerFunction("top", new StandardSQLFunction("top"));
	}
}
