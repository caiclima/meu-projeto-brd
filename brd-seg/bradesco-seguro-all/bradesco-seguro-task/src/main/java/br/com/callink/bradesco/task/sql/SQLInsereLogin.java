package br.com.callink.bradesco.task.sql;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;

/**
 * Insere informações de login de usuário.
 * 
 * @author michael
 *
 */
public class SQLInsereLogin implements ISQL {
	private String owner = "";

	public SQLInsereLogin withOwner(String owner) {
		if (owner != null) {
			this.owner = owner;
		}
		return this;
	}

	@Override
	public String getSQL(Object... args) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" INSERT INTO " + owner + " TB_LOGIN ");
		sql.append(" (MATRICULA, DATA_INICIO, CODIGO_DOMINIO, DATA_FIM, LOGIN, DATA_CADASTRO, DATA_ATUALIZACAO, ATIVO, ID_USUARIO) ");
		sql.append(" VALUES ");
		sql.append(" (%s, '%s', %s, '%s', '%s', '%s', '%s', 1, %s) ");

		return noNullable( StringUtils.format(sql.toString(), args) );
	}
	
	private String noNullable(String sql){
		return sql.replaceAll("'null'", "null");
	}

}
