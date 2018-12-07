package br.com.callink.bradesco.task.sql;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;

/**
 * Desativa um login de usuário
 * 
 * @author michael
 *
 */
public class SQLDesativaLogin implements ISQL {
	private String owner = "";

	public SQLDesativaLogin withOwner(String owner) {
		if (owner != null) {
			this.owner = owner;
		}
		return this;
	}

	@Override
	public String getSQL(Object... args) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" UPDATE " + owner + " TB_LOGIN ");
		sql.append(" SET ATIVO = 0 ");
		sql.append(" WHERE codigo_dominio = %s AND login = '%s' ");

		return StringUtils.format(sql.toString(), args);
	}

}
