package br.com.callink.bradesco.task.sql;


/**
 * Busca todos IDs de usuários com suas respectivas matrículas.
 * 
 * @author michael
 * 
 */
public class SQLBuscaIDsUsuarioComMatricula implements ISQL {
	private String owner = "";

	public SQLBuscaIDsUsuarioComMatricula withOwner(String owner) {
		if (owner != null) {
			this.owner = owner;
		}
		return this;
	}

	@Override
	public String getSQL(Object... args) {
		return " SELECT DISTINCT ID_USUARIO as id, COD_MATRICULA as matricula from " + owner + " TB_USUARIO WITH (NOLOCK) ";
	}
}
