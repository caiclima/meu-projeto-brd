package br.com.callink.bradesco.task.sql;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;

/**
 * Busca logins de usuários.
 * 
 * @author michael
 * 
 */
public class SQLBuscaLogin implements ISQL {
	private String owner = "";

	public SQLBuscaLogin withOwner(String owner) {
		if (owner != null) {
			this.owner = owner;
		}
		return this;
	}

	@Override
	public String getSQL(Object... args) {
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT	DISTINCT l.matricula as matricula, ");
		sql.append(" 		l.data_inicio as dataInicio, ");
		sql.append(" 		l.codigo_dominio as codigoDominio, ");
		sql.append(" 		l.data_fim as dataFim, ");
		sql.append(" 		l.usuario as login, ");
		sql.append(" 		l.data_cadastro as dataCadastro ");
		sql.append(" FROM	" + owner + " tb_login l with (nolock) ");
		sql.append(" INNER JOIN " + owner + " tb_pessoa p with (nolock) on p.ID_PESSOA = l.ID_PESSOA ");
		sql.append(" WHERE	p.ID_OPERACAO = %s AND l.codigo_dominio in (%s)");
		
		return StringUtils.format(sql.toString(), args);
	}
}
