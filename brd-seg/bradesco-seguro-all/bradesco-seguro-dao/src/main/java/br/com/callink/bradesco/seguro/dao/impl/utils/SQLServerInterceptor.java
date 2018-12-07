package br.com.callink.bradesco.seguro.dao.impl.utils;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.EmptyInterceptor;

/**
 * Default interceptor. Aplica regex para inserir 'with (nolock)' nas consultas. Também suporta a function registrada TOP (SQL SERVER), cujo uso segue:
 * 
 * TOP('NUMERO DE LINHAS','COLUNA').
 * EXEMPLO: select TOP('1','nome') from Usuario
 * 
 * @author michael
 * 
 */
public class SQLServerInterceptor extends EmptyInterceptor {
	private static final long serialVersionUID = 1L;

	@Override
	public synchronized String onPrepareStatement(String sql) {
		if (sql != null) {
			sql = sql.toLowerCase();
			if (!sql.contains("delete") && !sql.contains("insert") && !sql.contains("update") && sql.contains("select")) {
				sql = sql.replaceAll("(tb[\\w_]+)\\s+([\\w_0-9]*\\s*)", " $1 $2 with (nolock) ");
			}
			return doTop(sql);
		}
		return sql;
	}
	
	private String doTop(String sql) {
		Pattern pattern = Pattern.compile("top(\\((.+?)\\))");
		Matcher matcher = pattern.matcher(sql);
		while (matcher.find()) {
			String grp = matcher.group(1);
			String topStatement = grp.replaceAll("[()']", "");
			sql = sql.replaceAll(grp.replace("(", "\\(").replace(")", "\\)"), MessageFormat.format(" {0} {1} ", topStatement.split(",")[0], topStatement.split(",")[1]));
		}
		return sql;
	}
}
