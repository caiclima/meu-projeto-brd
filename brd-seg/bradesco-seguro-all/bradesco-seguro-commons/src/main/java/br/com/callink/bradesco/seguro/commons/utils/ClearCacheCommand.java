package br.com.callink.bradesco.seguro.commons.utils;


/**
 * @author swb_samuel
 *
 */
public enum ClearCacheCommand {
	EVENTO,
	TIPO_EVENTO,
	PARAMETRO_SISTEMA,
    PRODUTO,
    CARGO,
    ESPORTE,
    PROFISSAO,
    PLANO,
    GRAU_PARENTESCO,
    ESTADO_CIVIL,
    TIPO_PLANO
	;
	
	private String queryString;
	
	ClearCacheCommand(){
		this.queryString = "?command=" + this.name();
	}
	
	public String getQueryString() {
		return queryString;
	}

	public static ClearCacheCommand getInstance(String name){
		ClearCacheCommand[] values = ClearCacheCommand.values();
		
		ClearCacheCommand ret = null;
		for (ClearCacheCommand command : values) {
			if(command.name().equals(name)){
				ret = command;
				break;
			}
		}
		
		return ret;
	}
}
