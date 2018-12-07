package br.com.callink.bradesco.task.sql;


/**
 * Busca os Pessoas.
 * 
 * @author neppo.oldamar
 * 
 */
public class SQLBuscaPessoas implements ISQL {
	private String owner = "";

	public SQLBuscaPessoas withOwner(String owner) {
		if (owner != null) {
			this.owner = owner;
		}
		return this;
	}

	@Override
	public String getSQL(Object... args) {
		return " {CALL " + owner + "SP_RECUPERA_PESSOAS_POR_OPERACAO_AND_CARGO(?, ?)} ";
		
		//return StringUtils.format(sql.toString(), args);
	}
}

