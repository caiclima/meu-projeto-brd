package br.com.callink.bradesco.task.sql;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;


/**
 * SQL para buscar Parâmetro de Sistema
 * 
 * @author michael
 * 
 */
public class SQLBuscaParametroSistema implements ISQL {
	private String owner = "";

	public SQLBuscaParametroSistema withOwner(String owner) {
		if (owner != null) {
			this.owner = owner;
		}
		return this;
	}
	

	@Override
	public String getSQL(Object... args) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT VALOR_PARAMETRO_SISTEMA ");
		sql.append(" FROM   " + owner + " TB_PARAMETRO_SISTEMA with (nolock) ");
		sql.append(" WHERE  NOME_PARAMETRO_SISTEMA = '%s'");
		
		return StringUtils.format(sql.toString(), args);
	}
}
