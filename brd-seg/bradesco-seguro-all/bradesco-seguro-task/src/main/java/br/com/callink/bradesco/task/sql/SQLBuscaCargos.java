package br.com.callink.bradesco.task.sql;


/**
 * Busca os Cargos.
 * 
 * @author michael
 * 
 */
public class SQLBuscaCargos implements ISQL {
	private String owner = "";

	public SQLBuscaCargos withOwner(String owner) {
		if (owner != null) {
			this.owner = owner;
		}
		return this;
	}

	@Override
	public String getSQL(Object... args) {
		return " SELECT DISTINCT ID_CARGO as id, NOM_CARGO as nomeCargo, IND_ATIVO as ativo FROM " + owner + " TB_CARGO WITH (NOLOCK) ";
	}
}
