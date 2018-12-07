package br.com.callink.bradesco.task.sql;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;

/**
 * Atualiza informações de usuário
 * 
 * @author michael
 *
 */
public class SQLAtualizaUsuario implements ISQL {
	private String owner = "";

	public SQLAtualizaUsuario withOwner(String owner) {
		if (owner != null) {
			this.owner = owner;
		}
		return this;
	}

	@Override
	public String getSQL(Object... args) {
		StringBuilder sql = new StringBuilder();

		sql.append(" UPDATE " + owner + " TB_USUARIO ");
		sql.append(" SET nome_usuario = '%s', cod_centro_custo = %s, descricao_centro_custo = '%s', data_cadastro = '%s', data_atualizacao = '%s', id_cargo = %s ");
		sql.append(" WHERE id_usuario = %s ");

		return StringUtils.format(sql.toString(), args);
	}

}
