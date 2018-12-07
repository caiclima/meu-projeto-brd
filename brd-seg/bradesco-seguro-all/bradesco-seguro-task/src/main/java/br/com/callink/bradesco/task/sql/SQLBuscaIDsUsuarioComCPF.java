package br.com.callink.bradesco.task.sql;


/**
 * Busca todos IDs de usuários com seus respectivos CPFs.
 * 
 * @author michael
 * 
 */
public class SQLBuscaIDsUsuarioComCPF implements ISQL {
	private String owner = "";

	public SQLBuscaIDsUsuarioComCPF withOwner(String owner) {
		if (owner != null) {
			this.owner = owner;
		}
		return this;
	}

	@Override
	public String getSQL(Object... args) {
		return " SELECT DISTINCT ID_USUARIO as id, NUM_CPF as cpf from " + owner + " TB_USUARIO WITH (NOLOCK) ";
	}
}
