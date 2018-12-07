package br.com.callink.bradesco.task.sql;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;

/**
 * Insere novo cargo
 * 
 * @author michael
 *
 */
public class SQLInsereCargo implements ISQL {
	private String owner = "";

	public SQLInsereCargo withOwner(String owner) {
		if (owner != null) {
			this.owner = owner;
		}
		return this;
	}

	@Override
	public String getSQL(Object... args) {
		StringBuilder sql = new StringBuilder();

		sql.append(" INSERT INTO " + owner + " TB_CARGO ");
		sql.append(" (NOME_CARGO) ");
		sql.append(" VALUES ('%s') ");

		return StringUtils.format(sql.toString(), args);
	}

}
