package br.com.callink.bradesco.task.sincronizadores;

import br.com.callink.bradesco.dao.ICorporativoDAO;

public abstract class Sincronizador {

	//protected final static String MSG_BUNDLE = "bundle";
	protected ICorporativoDAO corporativoDAO;
	
	public abstract void sincroniza();
}
