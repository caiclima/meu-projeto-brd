package br.com.callink.bradesco.task.sql;


/**
 * Busca os Dominios.
 * 
 * @author neppo.oldamar
 * 
 */
public class SQLBuscaDominios implements ISQL {
	private String owner = "";

	public SQLBuscaDominios withOwner(String owner) {
		if (owner != null) {
			this.owner = owner;
		}
		return this;
	}

	@Override
	public String getSQL(Object... args) {
		return " SELECT DISTINCT ID_DOMINIO as id, DES_DOMINIO as descricao, OBS_DOMINIO as observacao, DAT_CADASTRO as dataCadastro, IND_ATIVO as ativo"
				+ " FROM " + owner + " TB_DOMINIO WITH (NOLOCK) ";
	}
}

