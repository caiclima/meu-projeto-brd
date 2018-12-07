package br.com.callink.bradesco.task.sql;

import br.com.callink.bradesco.seguro.commons.utils.StringUtils;

/**
 * Insere novo usuário
 * 
 * @author michael
 *
 */
public class SQLInsereUsuario implements ISQL {
	private String owner = "";

	public SQLInsereUsuario withOwner(String owner) {
		if (owner != null) {
			this.owner = owner;
		}
		return this;
	}

	@Override
	public String getSQL(Object... args) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" INSERT INTO " + owner + " TB_USUARIO " );
		sql.append(" (NOME_USUARIO, Flag_Enabled, COD_MATRICULA, NUM_CPF, COD_CENTRO_CUSTO, DESCRICAO_CENTRO_CUSTO, DATA_CADASTRO, DATA_ATUALIZACAO, ID_CARGO) ");
		sql.append(" VALUES ");
		sql.append(" ('%s', 1, %s, '%s', %s, '%s', '%s', '%s', %s) ");

		return StringUtils.format(sql.toString(), args);
	}

}
