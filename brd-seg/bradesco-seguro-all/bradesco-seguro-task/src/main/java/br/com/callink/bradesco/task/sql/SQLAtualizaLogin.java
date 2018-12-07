package br.com.callink.bradesco.task.sql;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;

/**
 * Efetua atualização de informações de login
 * 
 * @author michael
 *
 */
public class SQLAtualizaLogin implements ISQL {
	private String owner = "";

	public SQLAtualizaLogin withOwner(String owner) {
		if (owner != null) {
			this.owner = owner;
		}
		return this;
	}

	@Override
	public String getSQL(Object... args) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" UPDATE " + owner + " TB_LOGIN ");
		sql.append(" SET data_inicio = '%s', data_fim = '%s', matricula = %s, data_cadastro = '%s', data_atualizacao = '%s'");
		sql.append(" WHERE codigo_dominio = %s AND login = '%s' ");

		return noNullable( StringUtils.format(sql.toString(), args) );
	}

	private String noNullable(String sql){
		return sql.replaceAll("'null'", "null");
	}
}
